package com.delivery.api.domain.user.controller;

import com.delivery.api.common.api.Api;
import com.delivery.api.domain.user.business.UserBusiness;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/users")
public class UserOpenApiController {
    private final UserBusiness userBusiness;

    @PostMapping
    public Api<UserResponse> register(@Valid @RequestBody Api<UserRegisterRequest> request) {
        var response = userBusiness.register(request.getBody());

        return Api.OK(response);
    }
}
