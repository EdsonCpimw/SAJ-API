package com.saj.api.modules.users.docs;

import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

import java.util.List;


@Tag(name = "Usuários", description = "Endpoints para usuários")
public interface UserControllerDocs {

    @Operation(
            summary = "Listar Usuários",
            description = "Endpoint para listar todos os usuários",
            responses = {
                    @ApiResponse(
                            description = "Sucesso",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            array = @ArraySchema(schema = @Schema(implementation = UsersResponseDTO.class))
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Sem permissão de acesso",
                            content = @Content
                    )
            }
    )
    @GetMapping
    ResponseEntity<List<UsersResponseDTO>> findAllUsers();

}
