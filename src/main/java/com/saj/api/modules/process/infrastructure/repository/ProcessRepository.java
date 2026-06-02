package com.saj.api.modules.process.infrastructure.repository;

import com.saj.api.modules.process.domain.entities.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, UUID>, JpaSpecificationExecutor<Process> {
}
