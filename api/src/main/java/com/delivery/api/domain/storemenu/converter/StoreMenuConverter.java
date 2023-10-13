package com.delivery.api.domain.storemenu.converter;

import com.delivery.api.common.annotation.Converter;
import com.delivery.api.common.error.ErrorCode;
import com.delivery.api.common.exception.ApiException;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import com.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import com.delivery.db.storemenu.StoreMenuEntity;

import java.util.Optional;

@Converter
public class StoreMenuConverter {

    public StoreMenuEntity toEntity(StoreMenuRegisterRequest request) {
        return Optional.ofNullable(request)
                .map(it -> StoreMenuEntity.builder()
                        .storeId(request.getStoreId())
                        .name(request.getName())
                        .amount(request.getAmount())
                        .thumbnailUrl(request.getThumbnailUrl())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public StoreMenuResponse toResponse(StoreMenuEntity storeMenuEntity) {
        return Optional.ofNullable(storeMenuEntity)
                .map(it -> StoreMenuResponse.builder()
                        .id(storeMenuEntity.getId())
                        .storeId(storeMenuEntity.getStoreId())
                        .amount(storeMenuEntity.getAmount())
                        .status(storeMenuEntity.getStatus())
                        .thumbnailUrl(storeMenuEntity.getThumbnailUrl())
                        .likeCount(storeMenuEntity.getLikeCount())
                        .sequence(storeMenuEntity.getSequence())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
