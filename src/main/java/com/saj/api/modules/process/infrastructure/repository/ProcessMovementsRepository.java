package com.saj.api.modules.process.infrastructure.repository;

import com.saj.api.modules.process.domain.entities.ProcessMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProcessMovementsRepository extends JpaRepository<ProcessMovements, UUID> {

    @Query("SELECT pm FROM ProcessMovements pm JOIN FETCH pm.process WHERE pm.process.id = :processId")
    List<ProcessMovements> findByProcess_Id(@Param("processId") UUID processId);

    @Query("""
        SELECT pm
        FROM ProcessMovements pm
        JOIN FETCH pm.process p
        JOIN FETCH p.client c
        WHERE pm.id = :id
    """)
    Optional<ProcessMovements> findByIdWithClient(@Param("id") UUID id);
}
