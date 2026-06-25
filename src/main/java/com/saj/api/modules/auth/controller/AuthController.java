package com.saj.api.modules.auth.controller;

import com.saj.api.modules.auth.controller.dtos.ForgotPasswordDTO;
import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
import com.saj.api.modules.auth.controller.dtos.ResetPasswordDTO;
import com.saj.api.modules.auth.docs.AuthControllerDocs;
import com.saj.api.modules.auth.service.AuthService;
import com.saj.api.modules.auth.service.RegisterService;
import com.saj.api.shared.dto.SuccessResponseDTO;
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

    private final RegisterService registerService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        registerService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<SuccessResponseDTO> forgotPassword(@Valid @RequestBody ForgotPasswordDTO dto) {
        authService.forgotPassword(dto.email());
        return ResponseEntity.ok(SuccessResponseDTO.of(
                "Se o e-mail estiver cadastrado, você receberá as instruções em breve por email"
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<SuccessResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        authService.resetPassword(dto);
        return ResponseEntity.ok(SuccessResponseDTO.of("Senha redefinida com sucesso"));
    }
}
