package com.saj.api.modules.process.controller;

import com.saj.api.modules.process.controller.dtos.movements.CreateMovementsDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementResponseDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.controller.dtos.movements.UpdateMovementDTO;
import com.saj.api.modules.process.docs.ProcessMovementsControllerDocs;
import com.saj.api.modules.process.service.ProcessMovementsService;
import com.saj.api.shared.dto.SuccessResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/process-movements")
public class ProcessMovementsController implements ProcessMovementsControllerDocs {

    private final ProcessMovementsService processMovementsService;

    @GetMapping("/{id}")
    public ResponseEntity<ProcessMovementResponseDTO> findMovementsById(@PathVariable UUID id) {
        var processMovement = processMovementsService.findMovementById(id);
        return ResponseEntity.ok(processMovement);
    }

    @GetMapping("/process/{id}")
    public ResponseEntity<List<ProcessMovementsResponseDTO>> findMovementsByProcessId(@PathVariable UUID id) {
        var processMovements = processMovementsService.findMovementsByProcessId(id);
        return ResponseEntity.ok(processMovements);
    }

    @PostMapping
    public ResponseEntity<Void> createProcessMovement(@Valid @RequestBody CreateMovementsDTO createMovementsDTO) {
        processMovementsService.createProcessMovement(createMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> updateProcessMovement(@PathVariable UUID id, @Valid @RequestBody UpdateMovementDTO dto) {
        processMovementsService.updateProcessMovement(id, dto);
        return ResponseEntity.ok(SuccessResponseDTO.of("Movimentação de processo atualizado"));
    }
}



