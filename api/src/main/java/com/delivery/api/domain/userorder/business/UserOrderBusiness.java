package com.delivery.api.domain.userorder.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.store.converter.StoreConverter;
import com.delivery.api.domain.store.service.StoreService;
import com.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.api.domain.userorder.converter.UserOrderConverter;
import com.delivery.api.domain.userorder.service.UserOrderService;
import com.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import com.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public UserOrderResponse userOrder(User user, UserOrderRequest request) {
        var storeMenuEntityList = request.getStoreMenuIdList().stream()
                .map(storeMenuService::getStoreMenuWithThrow)
                .collect(Collectors.toList());

        var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it -> userOrderMenuConverter.toEntity(newUserOrderEntity, it))
                .collect(Collectors.toList());
        userOrderMenuEntityList.forEach(userOrderMenuService::order);

        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {
        var userOrderEntityList = userOrderService.current(user.getId());
        return userOrderEntityList.stream()
                .map(it -> {
                    var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
                    var storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(userOrderMenuEntity -> storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()))
                            .collect(Collectors.toList());

                    // TODO 리팩토링 필요
                    var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream()
                            .findFirst().get().getStoreId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<UserOrderDetailResponse> history(User user) {
        var userOrderEntityList = userOrderService.history(user.getId());
        return userOrderEntityList.stream()
                .map(it -> {
                    var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
                    var storeMenuEntityList = userOrderMenuEntityList.stream()
                            .map(userOrderMenuEntity -> storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()))
                            .collect(Collectors.toList());

                    // TODO 리팩토링 필요
                    var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream()
                            .findFirst().get().getStoreId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(it))
                            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public UserOrderDetailResponse read(User user, Long orderId) {
        var userOrderEntity = userOrderService.getUserOrderWithoutStatusWithThrow(orderId, user.getId());

        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());
        var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId()))
                .collect(Collectors.toList());

        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream()
                .findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build();
    }
}
