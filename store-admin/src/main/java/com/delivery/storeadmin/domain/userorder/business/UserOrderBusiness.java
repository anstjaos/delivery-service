package com.delivery.storeadmin.domain.userorder.business;

import com.delivery.common.message.model.UserOrderMessage;
import com.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import com.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import com.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import com.delivery.storeadmin.domain.userorder.service.UserOrderService;
import com.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    public void pushUserOrder(UserOrderMessage userOrderMessage) {
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
                .orElseThrow(() -> new RuntimeException("사용자 주문 내역 없음"));

        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        var storeMenuResponseList = userOrderMenuList.stream()
                .map(userOrderMenuEntity -> storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()))
                .map(storeMenuConverter::toResponse)
                .collect(Collectors.toList());

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        var push = UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderResponse)
                .storeMenuResponseList(storeMenuResponseList)
                .build();

        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        // 사용자에게 push
        userConnection.sendMessage(push);
    }
}
