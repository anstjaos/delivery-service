package com.delivery.api.domain.store.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import com.delivery.db.store.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {

    public StoreEntity toEntity(StoreRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it ->StoreEntity.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .category(request.getStoreCategory())
                    .minimumAmount(request.getMinimumAmount())
                    .minimumDeliveryAmount(request.getMinimumDeliveryAmount())
                    .thumbnailUrl(request.getThumbnailUrl())
                    .phoneNumber(request.getPhoneNumber())
                    .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreResponse toResponse(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity)
                    .map(it -> StoreResponse.builder()
                            .id(storeEntity.getId())
                            .name(storeEntity.getName())
                            .status(storeEntity.getStatus())
                            .category(storeEntity.getCategory())
                            .address(storeEntity.getAddress())
                            .minimumAmount(storeEntity.getMinimumAmount())
                            .minimumDeliveryAmount(storeEntity.getMinimumDeliveryAmount())
                            .thumbnailUrl(storeEntity.getThumbnailUrl())
                            .phoneNumber(storeEntity.getPhoneNumber())
                            .star(storeEntity.getStar())
                            .build())
                    .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
