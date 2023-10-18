package com.delivery.storeadmin.domain.authorization;

import com.delivery.storeadmin.domain.user.service.StoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {

    private final StoreUserService storeUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var storeUserEntity = storeUserService.getRegisterUser(username);

        return storeUserEntity.map(it -> User.builder()
                        .username(it.getEmail())
                        .password(it.getPassword())
                        .roles(it.getRole().toString())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
