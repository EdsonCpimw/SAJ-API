package com.saj.api.modules.users.service;

import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.infrastructure.repository.CompanyRepository;
import com.saj.api.shared.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private static final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;


    public Company createCompany(Company company) {
        var newCompany = companyRepository.save(company);
        return newCompany;
    }

    public Company companyFindById(UUID id) {
        log.info("Buscando empresa pelo id: {}", id);

        return companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Escritório não encontrada pelo id: {}", id);
                    return new ObjectNotFoundException("Escritório não encontrado pelo id: " + id);
        });
    }
}
