package com.delivery.api.domain.userorder.controller;

import com.delivery.api.common.annotation.UserSession;
import com.delivery.api.common.api.Api;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.business.UserOrderBusiness;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-orders")
public class UserOrderApiController {
    private final UserOrderBusiness userOrderBusiness;

    @PostMapping
    public Api<UserOrderResponse> userOrder(@Valid @RequestBody Api<UserOrderRequest> request,
                                            @Parameter(hidden = true) @UserSession User user) {
        var response = userOrderBusiness.userOrder(user, request.getBody());

        return Api.OK(response);
    }
}
