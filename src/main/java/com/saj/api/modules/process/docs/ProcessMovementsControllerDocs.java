package com.saj.api.modules.process.docs;

import com.saj.api.modules.process.controller.dtos.movements.CreateMovementsDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.shared.exceptions.dtos.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Processos")
public interface ProcessMovementsControllerDocs {

    @Operation(
            summary = "Buscar Movimentação do processo",
            description = "Buscar um movimentação do processo no sistema",
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
                            description = "Dados da movimentação do processo",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProcessMovementsResponseDTO.class))

                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Movimentação do processo não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "status": 404,
                                                      "message": "Não foi encontado movimentos para o processo informado",
                                                      "timestamp": "2026-05-30T17:23:30.225002"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<List<ProcessMovementsResponseDTO>> findMovementsById(@PathVariable UUID id);


    @Operation(
            summary = "Cadastrar Movimentação de processo",
            description = "Cadastrar um nova movimentação de processo no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateMovementsDTO.class)
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
                                                      "message": "Processo não encontrado",
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
                                                          "field": "title",
                                                          "message": "Título é obrigatório"
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
    ResponseEntity<Void> createProcessMovement(@Valid @RequestBody CreateMovementsDTO createMovementsDTO);

}
