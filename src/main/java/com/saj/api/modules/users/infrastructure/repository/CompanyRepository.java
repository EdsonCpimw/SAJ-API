package com.saj.api.modules.users.infrastructure.repository;

import com.saj.api.modules.users.domain.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

}
