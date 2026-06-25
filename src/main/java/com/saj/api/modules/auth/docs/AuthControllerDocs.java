package com.saj.api.modules.auth.docs;

import com.saj.api.modules.auth.controller.dtos.ForgotPasswordDTO;
import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
import com.saj.api.modules.auth.controller.dtos.ResetPasswordDTO;
import com.saj.api.shared.dto.SuccessResponseDTO;
import com.saj.api.shared.exceptions.dtos.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Endpoints para autenticação")
public interface AuthControllerDocs {

    @Operation(
            summary = "Registrar Usuário",
            description = "Registrar um novo usuário no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisterRequestDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário cadastrado com sucesso"

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Violação de dados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 422,
                                                      "message": "Erro de validação",
                                                      "timestamp": "2026-05-28T15:00:00",
                                                      "errors": [
                                                        { "field": "user.email", "message": "E-mail inválido" }
                                                      ]
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PostMapping("/register")
    ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO);


    @Operation(
            summary = "Trocar senha",
            description = "Efetuar a troca de senha do usuário no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResetPasswordDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Senha definida com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 200,
                                                      "message": "Senha redefinida com sucesso",
                                                      "timestamp": "2026-05-31T17:12:31.547721"
                                                    }
                                                    """
                                    )
                            )

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Token já utilizado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 400,
                                                      "message": "Token já utilizado - solicite um novo reset",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    @PostMapping("/reset-password")
    ResponseEntity<SuccessResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO dto);

    @Operation(
            summary = "Esqueci minha senha",
            description = "Solicitar a troca de senha para o usuário do sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResetPasswordDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se o e-mail estiver cadastrado, você receberá as instruções em breve por email",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 200,
                                                      "message": "Se o e-mail estiver cadastrado, você receberá as instruções em breve por email",
                                                      "timestamp": "2026-05-31T17:12:31.547721"
                                                    }
                                                    """
                                    )
                            )

                    ),
            }
    )
    @PostMapping("/forgot-password")
    ResponseEntity<SuccessResponseDTO> forgotPassword(@Valid @RequestBody ForgotPasswordDTO dto);
}
