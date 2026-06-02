package com.saj.api.modules.process.docs;

import com.saj.api.modules.process.controller.dtos.ProcessResponseDTO;
import com.saj.api.modules.process.controller.dtos.ProcessSearchDTO;
import com.saj.api.modules.process.domain.enums.LegalArea;
import com.saj.api.modules.process.domain.enums.ProcessPriority;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import com.saj.api.shared.dto.PaginationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
                              "createdAt": "2024-01-15T10:30:00"
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
}
