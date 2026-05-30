package com.saj.api.modules.users.docs;

import com.saj.api.modules.users.controller.dtos.UserSearchDTO;
import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.shared.dto.PagenationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.ModelAttribute;


@Tag(name = "Usuários", description = "Endpoints para usuários")
public interface UserControllerDocs {

    @Operation(
            summary = "Listar Usuários",
            description = "Endpoint para listar usuários com filtros, paginação e ordenação",
            parameters = {

                    @Parameter(
                            name = "search",
                            description = "Buscar pelo nome da empresa, nome, email ou telefone",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string")
                    ),

                    @Parameter(
                            name = "active",
                            description = "Filtrar usuários ativos ou inativos",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "boolean")
                    ),

                    @Parameter(
                            name = "page",
                            description = "Número da página (inicia em 0)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", defaultValue = "0")
                    ),

                    @Parameter(
                            name = "size",
                            description = "Quantidade de registros por página",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "integer", defaultValue = "10")
                    ),

                    @Parameter(
                            name = "sortBy",
                            description = "Campo utilizado para ordenação",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string", defaultValue = "name")
                    ),

                    @Parameter(
                            name = "direction",
                            description = "Direção da ordenação (asc ou desc)",
                            in = ParameterIn.QUERY,
                            schema = @Schema(
                                    type = "string",
                                    allowableValues = {"asc", "desc"},
                                    defaultValue = "asc"
                            )
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                        {
                          "content": [
                            {
                              "id": "b9f44c5f-fc9a-4d75-bf7d-cbde6b3c83e1",
                              "name": "João Silva",
                              "email": "joao@email.com",
                              "active": true
                            }
                          ],
                          "totalElements": 150,
                          "totalPages": 15,
                          "page": 0,
                          "size": 10,
                          "first": true,
                          "last": false
                        }
                        """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Sem permissão de acesso",
                            content = @Content
                    )
            }
    )
    @GetMapping
    ResponseEntity<PagenationResponseDTO<UsersResponseDTO>> findAllUsers(
            @Parameter(hidden = true) @ModelAttribute UserSearchDTO filter

    );


}
