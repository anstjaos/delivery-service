package com.delivery.api.domain.userorder.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import com.delivery.api.domain.userorder.converter.UserOrderConverter;
import com.delivery.api.domain.userorder.service.UserOrderService;
import com.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import com.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {
    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final StoreMenuService storeMenuService;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;

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
}
