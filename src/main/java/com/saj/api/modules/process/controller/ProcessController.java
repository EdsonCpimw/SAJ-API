package com.saj.api.modules.process.controller;

import com.saj.api.modules.process.controller.dtos.CreateProcessDTO;
import com.saj.api.modules.process.controller.dtos.ProcessResponseDTO;
import com.saj.api.modules.process.controller.dtos.ProcessSearchDTO;
import com.saj.api.modules.process.controller.dtos.UpdateProcessDTO;
import com.saj.api.modules.process.docs.ProcessControllerDocs;
import com.saj.api.modules.process.service.ProcessService;
import com.saj.api.shared.dto.PaginationResponseDTO;
import com.saj.api.shared.dto.SuccessResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/process")
public class ProcessController implements ProcessControllerDocs {

    private final ProcessService processService;

    @GetMapping
    public ResponseEntity<PaginationResponseDTO<ProcessResponseDTO>> findAllProcess(@ModelAttribute ProcessSearchDTO filter) {
        return ResponseEntity.ok(processService.findAllProcessSearch(filter));
    }

    @PostMapping
    public ResponseEntity<Void> createProcess(@Valid @RequestBody CreateProcessDTO createProcessDTO) {
        processService.createProcess(createProcessDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> updateProcess(UUID id, @Valid @RequestBody UpdateProcessDTO updateProcessDTO) {
        // chamar o service
        processService.updateProcess(id, updateProcessDTO);
        return ResponseEntity.ok(SuccessResponseDTO.of("Processo atualizado com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessResponseDTO> findProcessById(@PathVariable UUID id) {
        var process = processService.findProcessById(id);
        return ResponseEntity.ok(process);
    }
}
