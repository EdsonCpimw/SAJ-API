package com.saj.api.modules.process.infrastructure.repository;

import com.saj.api.modules.process.domain.entities.ProcessMovements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProcessMovementsRepository extends JpaRepository<ProcessMovements, UUID> {

    List<ProcessMovements> findByProcessId(UUID processId);
}
