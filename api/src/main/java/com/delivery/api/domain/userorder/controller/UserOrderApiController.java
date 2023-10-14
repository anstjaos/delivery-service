package com.delivery.api.domain.userorder.controller;

import com.delivery.api.common.annotation.UserSession;
import com.delivery.api.common.api.Api;
import com.delivery.api.domain.user.model.User;
import com.delivery.api.domain.userorder.business.UserOrderBusiness;
import com.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import com.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import com.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(@Parameter(hidden = true) @UserSession User user) {
        var response = userOrderBusiness.current(user);

        return Api.OK(response);
    }

    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(@Parameter(hidden = true) @UserSession User user) {
        var response = userOrderBusiness.history(user);

        return Api.OK(response);
    }

    @GetMapping("/{orderId}")
    public Api<UserOrderDetailResponse> read(@Parameter(hidden = true) @UserSession User user,
                                             @PathVariable Long orderId) {
        var response = userOrderBusiness.read(user, orderId);

        return Api.OK(response);
    }
}
