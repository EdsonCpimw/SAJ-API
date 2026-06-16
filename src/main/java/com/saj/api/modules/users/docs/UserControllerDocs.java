package com.saj.api.modules.users.docs;

import com.saj.api.modules.users.controller.dtos.*;
import com.saj.api.shared.dto.PaginationResponseDTO;
import com.saj.api.shared.dto.SuccessResponseDTO;
import com.saj.api.shared.exceptions.dtos.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.UUID;


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
                            schema = @Schema(
                                    type = "string",
                                    allowableValues = {"name", "email", "phone"},
                                    defaultValue = "name"
                            )
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
                          "data": [
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
    ResponseEntity<PaginationResponseDTO<UsersResponseDTO>> findAllUsers(
            @Parameter(hidden = true) @ModelAttribute UserSearchDTO filter

    );

    @Operation(
            summary = "Cadastrar Usuário",
            description = "Cadastrar um novo usuário no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuário cadastrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro ao cadastrar usuário",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 400,
                                                      "message": "Email já cadastrado",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Violação de dados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 422,
                                                      "message": "Erro de validação",
                                                      "timestamp": "2026-05-30T17:22:06.150131",
                                                      "errors": [
                                                        {
                                                          "field": "name",
                                                          "message": "Nome é obrigatório"
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PostMapping
    ResponseEntity<Void> createUser(@Valid  @RequestBody CreateUserDTO createUserDTO);

    @Operation(
            summary = "Atualizar Usuário",
            description = "Atualizar um usuário no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do usuário a ser atualizado",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid", example = "45dd1158-8fd0-432c-a444-5148fe6c4b6e")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateUserDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 200,
                                                      "message": "Usuário atualizado com sucesso",
                                                      "timestamp": "2026-05-31T17:12:31.547721"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro ao atualizar usuário",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 400,
                                                      "message": "Email já cadastrado",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Violação de dados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 422,
                                                      "message": "Erro de validação",
                                                      "timestamp": "2026-05-30T17:22:06.150131",
                                                      "errors": [
                                                        {
                                                          "field": "name",
                                                          "message": "Nome é obrigatório"
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<SuccessResponseDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDTO updateUserDTO);


    @Operation(
            summary = "Buscar Usuário",
            description = "Buscar um usuário no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do usuário",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid", example = "45dd1158-8fd0-432c-a444-5148fe6c4b6e")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dados do usuário",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "id": "40976e03-ead4-400e-a27c-c6630cffa64b",
                                                      "name": "Teste",
                                                      "email": "edson@gmail.com",
                                                      "phone": "(26) 37519-0748",
                                                      "active": true,
                                                      "companyName": "Escrito Edson Advogados"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuário não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 404,
                                                      "message": "Usuário não encontrado",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<UsersResponseDTO> findUserById(@PathVariable UUID id);


    @Operation(
            summary = "Inativar ou ativar Usuário",
            description = "Inativa ou ativa o usuário no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do usuário a ser inativado/ativado",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid", example = "45dd1158-8fd0-432c-a444-5148fe6c4b6e")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário inativado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 200,
                                                      "message": "Usuário inativado com sucesso",
                                                      "timestamp": "2026-05-31T17:12:31.547721"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro ineterno do servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 500,
                                                      "message": "Erro inerno do servidor",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    @PatchMapping("/{id}")
    ResponseEntity<SuccessResponseDTO> toggleUser(@PathVariable UUID id);

    @Operation(
            summary = "Listar clientes",
            description = "Endpoint para listar clientes com pesquisa",
            parameters = {

                    @Parameter(
                            name = "search",
                            description = "Buscar pelo nome ou email do cliente",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string")
                    ),
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = """
                                                    [
                                                      {
                                                        "id": "eac97e0a-04db-4b9d-8a4b-e40acd702469",
                                                        "name": "Juarez",
                                                        "email": "jua@email.com"
                                                      },
                                                      {
                                                        "id": "76bc5650-7213-4fdd-9472-5d632e946a83",
                                                        "name": "Jose",
                                                        "email": "jose@email.com"
                                                      }
                                                    ]
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Não autenticado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/clients")
    public ResponseEntity<List<ClientSearchResponseDTO>> searchClients(@RequestParam(required = false) String search);
}
