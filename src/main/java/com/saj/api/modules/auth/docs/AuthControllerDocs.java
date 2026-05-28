package com.saj.api.modules.auth.docs;

import com.saj.api.modules.auth.controller.dtos.RegisterRequestDTO;
import com.saj.api.shared.exceptions.dtos.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
}
