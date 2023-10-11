package com.delivery.api.domain.user.business;

import com.delivery.api.common.annotation.Business;
import com.delivery.api.domain.token.business.TokenBusiness;
import com.delivery.api.domain.token.controller.model.TokenResponse;
import com.delivery.api.domain.user.controller.model.UserLoginRequest;
import com.delivery.api.domain.user.controller.model.UserRegisterRequest;
import com.delivery.api.domain.user.controller.model.UserResponse;
import com.delivery.api.domain.user.converter.UserConverter;
import com.delivery.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class UserBusiness {
    private final UserService userService;
    private final UserConverter userConverter;
    private final TokenBusiness tokenBusiness;

    public UserResponse register(UserRegisterRequest request) {
        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);

        return userConverter.toResponse(newEntity);
    }

    public TokenResponse login(UserLoginRequest request) {
        var userEntity = userService.login(request.getEmail(), request.getPassword());

        return tokenBusiness.issueToken(userEntity);
    }

    public UserResponse me(Long userId) {
        var userEntity = userService.getUserWithThrow(userId);

        return userConverter.toResponse(userEntity);
    }
}
