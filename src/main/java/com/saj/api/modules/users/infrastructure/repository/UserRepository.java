package com.saj.api.modules.users.infrastructure.repository;

import com.saj.api.modules.users.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u JOIN FETCH u.company")
    List<User> findAllWithCompany();
}
