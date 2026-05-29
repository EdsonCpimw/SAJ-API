package com.saj.api.modules.auth.controller;

import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
import com.saj.api.modules.auth.docs.AuthControllerDocs;
import com.saj.api.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
