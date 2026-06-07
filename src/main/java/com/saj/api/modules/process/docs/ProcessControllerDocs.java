package com.saj.api.modules.process.docs;

import com.saj.api.modules.process.controller.dtos.process.*;
import com.saj.api.modules.process.domain.enums.LegalArea;
import com.saj.api.modules.process.domain.enums.ProcessPriority;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.shared.dto.PaginationResponseDTO;
import com.saj.api.shared.dto.SuccessResponseDTO;
import com.saj.api.shared.exceptions.dtos.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Processos", description = "Endpoints de processos")
public interface ProcessControllerDocs {

    @Operation(
            summary = "Listar Processos",
            description = "Endpoint para listar processos com filtros, paginação e ordenação",
            parameters = {

                    @Parameter(
                            name = "search",
                            description = "Buscar pelo nome da empresa, nome, email ou telefone",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "string")
                    ),

                    @Parameter(
                            name = "status",
                            description = "Filtrar usuários pelo status",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = ProcessStatus.class)
                    ),
                    @Parameter(
                            name = "priority",
                            description = "Filtrar usuários pela prioridade",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = ProcessPriority.class)
                    ),
                    @Parameter(
                            name = "legalArea",
                            description = "Filtrar usuários pela área jurídica",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = LegalArea.class)
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
                                    allowableValues = {"numberProcess", "title", "courtDivision"},
                                    defaultValue = "numberProcess"
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
                              "id": "6618e55a-1a00-464a-ad65-f109752ed57f",
                              "numberProcess": "0001234-12.2024.5.02.0001",
                              "title": "Labor Lawsuit - João Silva",
                              "status": "IN_PROGRESS",
                              "legalArea": "LABOR",
                              "courtDivision": "2ª Vara do Trabalho de São Paulo",
                              "court": "TRT-2",
                              "priority": "MEDIUM",
                              "createdAt": "2024-01-15T10:30:00",
                              "hasMovements": true
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
    ResponseEntity<PaginationResponseDTO<ProcessResponseDTO>> findAllProcess(@Parameter(hidden = true) @ModelAttribute ProcessSearchDTO filer);

    @Operation(
            summary = "Cadastrar Processo",
            description = "Cadastrar um novo processo no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateProcessDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Criado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro ao cadastrar processo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 404,
                                                      "message": "Usuário não encontrado",
                                                      "timestamp": "2026-06-02T18:47:59.191673"
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
                                                          "field": "numberProcess",
                                                          "message": "Numero do processo é obrigatório"
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro ao cadastrar processo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 500,
                                                      "message": "Erro interno no servidor",
                                                      "timestamp": "2026-06-02T18:44:41.716812"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    @PostMapping
    ResponseEntity<Void> createProcess(@Valid @RequestBody CreateProcessDTO createProcessDTO);

    @Operation(
            summary = "Atualizar Processo",
            description = "Atualizar um processo no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do processo a ser atualizado",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid", example = "6618e55a-1a00-464a-ad65-f109752ed57f")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateProcessDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Processo atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 200,
                                                      "message": "Processo atualizado com sucesso",
                                                      "timestamp": "2026-05-31T17:12:31.547721"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro ao atualizar processo",
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
    ResponseEntity<SuccessResponseDTO> updateProcess(@PathVariable UUID id, @Valid @RequestBody UpdateProcessDTO updateProcessDTO);

    @Operation(
            summary = "Buscar Processo",
            description = "Buscar um processo no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do processo",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid", example = "45dd1158-8fd0-432c-a444-5148fe6c4b6e")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dados do processo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "id": "21081643-a670-4dd6-b671-3a568a41b6e5",
                                                      "numberProcess": "7018452-34.2026.8.19.0023",
                                                      "title": "Ação para impostos compra automovel",
                                                      "description": "Ação para impostos devolvidos na compra de um automovel",
                                                      "status": "IN_PROGRESS",
                                                      "legalArea": "LABOR",
                                                      "courtDivision": "2ª Vara Cível de Campinas",
                                                      "court": "TJSP",
                                                      "priority": "URGENT",
                                                      "createdAt": "2026-06-02T22:43:18.092893"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Processo não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 404,
                                                      "message": "Processo não encontrado",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ProcessResponseDTO> findProcessById(@PathVariable UUID id);


    @Operation(
            summary = "Atualizar status do Processo",
            description = "Atualizar o status do processo no sistema",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do processo a ser atualizado",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "string", format = "uuid", example = "6618e55a-1a00-464a-ad65-f109752ed57f")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpadateStatusProcessDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Atualização do status com processo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 200,
                                                      "message": "Status do processo atualizado com sucesso",
                                                      "timestamp": "2026-05-31T17:12:31.547721"
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
                                                          "field": "status",
                                                          "message": "Status é obrigatório"
                                                        }
                                                      ]
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro ao atualizar status do processo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 500,
                                                      "message": "Erro interno no servidor",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    ResponseEntity<SuccessResponseDTO> updateProcessStatusById(@PathVariable UUID id, UpadateStatusProcessDTO dto);
}
