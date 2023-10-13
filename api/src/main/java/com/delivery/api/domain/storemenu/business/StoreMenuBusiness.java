package com.delivery.api.domain.storemenu.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import com.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import com.delivery.api.domain.storemenu.service.StoreMenuService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreMenuBusiness {
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    public StoreMenuResponse register(StoreMenuRegisterRequest request) {
        var entity = storeMenuConverter.toEntity(request);
        var newEntity = storeMenuService.register(entity);

        return storeMenuConverter.toResponse(newEntity);
    }

    public List<StoreMenuResponse> search(Long storeId) {
        var list = storeMenuService.getStoreMenuByStoreId(storeId);

        return list.stream()
                .map(storeMenuConverter::toResponse)
                .collect(Collectors.toList());
    }
}
