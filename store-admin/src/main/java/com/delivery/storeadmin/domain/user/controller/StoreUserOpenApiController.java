package com.delivery.storeadmin.domain.user.controller;

import com.delivery.storeadmin.domain.user.business.StoreUserBusiness;
import com.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import com.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-users")
public class StoreUserOpenApiController {
    private final StoreUserBusiness storeUserBusiness;

    @PostMapping
    public StoreUserResponse register(@Valid @RequestBody StoreUserRegisterRequest request) {
        var response = storeUserBusiness.register(request);
        return response;
    }
}
