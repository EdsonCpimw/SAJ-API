package com.saj.api.modules.auth.service;

import com.saj.api.modules.auth.controller.dtos.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthenticatedUser getAuthenticatedUser() {
        Jwt jwt = getJwt();
        return new AuthenticatedUser(
                jwt.getSubject(),
                jwt.getClaimAsString("name"),
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("give_name"),
                jwt.getClaimAsString("family_name")
        );
    }

    private static Jwt getJwt() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwtToken = (JwtAuthenticationToken) authentication;
        return jwtToken.getToken();
    }

}
