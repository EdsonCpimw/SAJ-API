package com.saj.api.modules.process.controller;

import com.saj.api.modules.process.controller.dtos.movements.CreateMovementsDTO;
import com.saj.api.modules.process.controller.dtos.movements.ProcessMovementsResponseDTO;
import com.saj.api.modules.process.docs.ProcessMovementsControllerDocs;
import com.saj.api.modules.process.service.ProcessMovementsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/process/movements")
public class ProcessMovementsController implements ProcessMovementsControllerDocs {

    private final ProcessMovementsService processMovementsService;

    @GetMapping("/{id}")
    public ResponseEntity<List<ProcessMovementsResponseDTO>> findMovementsById(@PathVariable UUID id) {
        var processMovements = processMovementsService.findMovementsByProcessId(id);
        return ResponseEntity.ok(processMovements);
    }

    @PostMapping
    public ResponseEntity<Void> createProcessMovement(@Valid @RequestBody CreateMovementsDTO createMovementsDTO) {
        processMovementsService.createProcessMovement(createMovementsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
