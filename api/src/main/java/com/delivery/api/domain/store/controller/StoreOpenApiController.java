package com.delivery.api.domain.store.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.store.business.StoreBusiness;
import com.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import com.delivery.api.domain.store.controller.model.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/stores")
public class StoreOpenApiController {
    private final StoreBusiness storeBusiness;

    @PostMapping
    public Api<StoreResponse> register(@Valid @RequestBody Api<StoreRegisterRequest> request) {
        var response = storeBusiness.register(request.getBody());

        return Api.OK(response);
    }
}
