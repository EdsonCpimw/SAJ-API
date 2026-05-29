package com.saj.api.modules.users.service;

import com.saj.api.modules.users.controller.dtos.UserSearchDTO;
import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.domain.mappers.UserMapper;
import com.saj.api.modules.users.infrastructure.repository.UserRepository;
import com.saj.api.modules.users.infrastructure.specifications.UserSpecification;
import com.saj.api.shared.dto.PageResponseDTO;
import com.saj.api.shared.exceptions.BusinessException;
import com.saj.api.shared.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("name", "email", "phone");

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public void validateEmailAlreadyExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Email já cadastrado");
        }
    }

    public void  saveUser(User user) {
        userRepository.save(user);
    }

    public List<UsersResponseDTO> findAllWithFilters(String companyName, String name, String email, String phone) {

        Specification<User> spec = Specification
                .where(UserSpecification.companyNameContains(companyName))
                .and(UserSpecification.nameContains(name))
                .and(UserSpecification.emailContains(email))
                .and(UserSpecification.phoneContains(phone));

        return userRepository.findAll(spec)
                .stream()
                .map(userMapper::toUsersResponseDTO)
                .toList();
    }

    public PageResponseDTO<UsersResponseDTO> findAllUsersSearch(UserSearchDTO filter) {

        Specification<User> spec = Specification
                .where(UserSpecification.search(filter.search()))
                .and(UserSpecification.isActive(filter.active()));

        int  page = filter.page() != null && filter.page() >= 0 ? filter.page() : 0;
        int size = filter.size() != null && filter.size() > 0 ? filter.size() : 10;

        String sortBy = filter.sortBy() != null && ALLOWED_SORT_FIELDS.contains(filter.sortBy())
                ? filter.sortBy() :
                "name";
        String direction = filter.direction() != null ? filter.direction() : "asc";

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Page<UsersResponseDTO> result = userRepository
                .findAll(spec, pageable)
                .map(userMapper:: toUsersResponseDTO);

        return PageUtils.from(result);
    }


}
