package com.saj.api.modules.process.controller;

import com.saj.api.modules.process.controller.dtos.ProcessResponseDTO;
import com.saj.api.modules.process.controller.dtos.ProcessSearchDTO;
import com.saj.api.modules.process.docs.ProcessControllerDocs;
import com.saj.api.modules.process.service.ProcessService;
import com.saj.api.shared.dto.PaginationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/process")
public class ProcessController implements ProcessControllerDocs {

    private final ProcessService processService;

    @GetMapping
    public ResponseEntity<PaginationResponseDTO<ProcessResponseDTO>> findAllProcess(@ModelAttribute ProcessSearchDTO filter) {
        return ResponseEntity.ok(processService.findAllProcessSearch(filter));
    }
}
