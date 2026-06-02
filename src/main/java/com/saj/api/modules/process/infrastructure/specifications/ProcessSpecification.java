package com.saj.api.modules.process.infrastructure.specifications;

import com.saj.api.modules.process.domain.entities.Process;
import com.saj.api.modules.process.domain.enums.LegalArea;
import com.saj.api.modules.process.domain.enums.ProcessPriority;
import com.saj.api.modules.process.domain.enums.ProcessStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProcessSpecification {
    private ProcessSpecification() {}

    public static Specification<Process> search(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isBlank()) return null;

            String pattern = "%" + search.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("numberProcess")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("courtDivision")), pattern)

            );
        };
    }

    public static Specification<Process> hasStatus(ProcessStatus status) {
        return (root, query, cb) -> status == null
                ? null
                : cb.equal(root.get("status"), status);
    }

    public static Specification<Process> hasLegalArea(LegalArea legalArea) {
        return (root, query, criteriaBuilder) -> legalArea == null
                ? null
                : criteriaBuilder.equal(root.get("legalArea"), legalArea);
    }

    public static  Specification<Process> hasProcessPriority(ProcessPriority priority) {
        return (root, query, criteriaBuilder) -> priority == null
                ? null
                : criteriaBuilder.equal(root.get("priority"), priority);
    }
}
