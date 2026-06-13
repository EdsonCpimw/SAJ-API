package com.saj.api.modules.users.infrastructure.specifications;

import com.saj.api.modules.users.domain.entities.Company;
import com.saj.api.modules.users.domain.entities.User;
import org.springframework.data.jpa.domain.Specification;

import javax.lang.model.element.NestingKind;

public class UserSpecification {
    private UserSpecification() {}

    public static Specification<User> companyNameContains(String companyName) {
        return (root, query, cb) -> companyName == null || companyName.isBlank() ? null
                : cb.like(cb.lower(root.join("company").get("name")), "%" + companyName.toLowerCase() + "%");
    }

    public static Specification<User> nameContains(String name) {
        return (root, query, cb) -> name == null || name.isBlank() ? null
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<User> emailContains(String email) {
        return (root, query, cb) -> email == null || email.isBlank() ? null
                : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<User> phoneContains(String phone) {
        return (root, query, cb) -> phone == null || phone.isBlank() ? null
                : cb.like(root.get("phone"), "%" + phone + "%");
    }

    public static Specification<User> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null
                : cb.equal(root.get("active"), active);
    }

    public static Specification<User> companyContains(Company company) {
        return (root, query, criteriaBuilder) -> company == null
                ? null
                : criteriaBuilder.equal(root.get("company"), company);
    }

    public static Specification<User> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) return null;

            String pattern = "%" + search.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("email")), pattern),
                    cb.like(cb.lower(root.get("phone")), pattern),
                    cb.like(cb.lower(root.join("company").get("name")), pattern)
            );
        };
    }
}
