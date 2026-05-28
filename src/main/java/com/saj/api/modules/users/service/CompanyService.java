package com.saj.api.modules.users.service;

import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.infrastructure.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;


    public Company createCompany(Company company) {
        var newCompany = companyRepository.save(company);
        return newCompany;
    }
}
