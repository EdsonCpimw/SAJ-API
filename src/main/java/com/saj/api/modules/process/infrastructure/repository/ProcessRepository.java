package com.saj.api.modules.process.infrastructure.repository;

import com.saj.api.modules.process.domain.entities.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProcessRepository extends JpaRepository<Process, UUID>, JpaSpecificationExecutor<Process> {

    @Query("""
            SELECT p FROM Process p
            LEFT JOIN FETCH p.client
            WHERE p.id = :id
            """)
    Optional<Process> findByIdWithClient(@Param("id") UUID id);
}
