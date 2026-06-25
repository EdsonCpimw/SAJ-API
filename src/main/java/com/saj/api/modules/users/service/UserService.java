package com.saj.api.modules.users.service;

import com.saj.api.modules.users.controller.dtos.*;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.domain.mappers.UserMapper;
import com.saj.api.modules.users.infrastructure.repository.UserRepository;
import com.saj.api.modules.users.infrastructure.specifications.UserSpecification;
import com.saj.api.shared.dto.PaginationResponseDTO;
import com.saj.api.shared.dto.SuccessResponseDTO;
import com.saj.api.shared.exceptions.BusinessException;
import com.saj.api.shared.exceptions.ObjectNotFoundException;
import com.saj.api.shared.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("name", "email", "phone");

    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;


    public void validateEmailAlreadyExists(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("Tentatica de cadsatro com email já existente: {}", email);
            throw new BusinessException("Email já cadastrado");
        }
    }

    public User  saveUser(User user) {
        return userRepository.save(user);
    }


    public void createUser(CreateUserDTO dto, User authenticatedUser) {
        log.info("Cadastrando usuário: {}", dto.email());
        validateEmailAlreadyExists(dto.email());
        var company = companyService.companyFindById(authenticatedUser.getCompany().getId());
        User user = userMapper.toUserCreate(dto, company, passwordEncoder.encode(dto.password()));
        this.saveUser(user);
        log.info("Usuário criado com sucesso. email: {}", dto.email());
    }

    public List<UsersResponseDTO> findAllWithFilters(String companyName, String name, String email, String phone, User authenticatedUser) {

        Specification<User> spec = Specification
                .where(UserSpecification.companyContains(authenticatedUser.getCompany()))
                .and(UserSpecification.companyNameContains(companyName))
                .and(UserSpecification.nameContains(name))
                .and(UserSpecification.emailContains(email))
                .and(UserSpecification.phoneContains(phone));

        return userRepository.findAll(spec)
                .stream()
                .map(userMapper::toUsersResponseDTO)
                .toList();
    }

    public PaginationResponseDTO<UsersResponseDTO> findAllUsersSearch(UserSearchDTO filter, User authenticatedUser) {

        Specification<User> spec = Specification
                .where(UserSpecification.companyContains(authenticatedUser.getCompany()))
                .and(UserSpecification.search(filter.search()))
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

    public void updateUser(UUID id, UpdateUserDTO dto) {
        log.info("Atualizando usuário... id: {}", id);
        var oldUser = findById(id);
        if(!oldUser.getEmail().equals(dto.email())) {
            this.validateEmailAlreadyExists(dto.email());
        }
        userMapper.updateUserFromDTO(dto, oldUser);
        userRepository.save(oldUser);

        log.info("Usuário atualizado com sucesso id: {}", id);
    }

    public User findById(UUID id) {
        log.info("Iniciando a busca do usuário pelo id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("Usuário não encontrado! id: {}", id);
            return new ObjectNotFoundException("Usuário não encontrado");
        });
        log.info("Usuário encontrado com sucesso.");
        return user;
    }

    public User findUserByEmail(String email) {
        log.info("Iniciando a busca do usuário pelo email: {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.warn("Usuário não encontrado! e-mail: {}", email);
            return new ObjectNotFoundException("Usuário não encontrado");
        });
        log.info("usuário encontrado com sucesso.");
        return user;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UsersResponseDTO findUserById(UUID id) {
        log.info("Buscando usuário pelo id: {}", id);
        return userMapper.toUsersResponseDTO(findById(id));
    }

    /*
    * TODO: Implementar a logica para verificar se id pertence a empresa do usuário logado
    */
    public SuccessResponseDTO toggleUserActive(UUID id) {
        log.info("Alterando status do usuário id: {}", id);
        var user = findById(id);
        user.setActive(!user.isActive());
        saveUser(user);

        String status = user.isActive() ? "ativado" : "inativado";
        log.info("Usuário {} com sucesso. id: {}", status, id);
        return SuccessResponseDTO.of("Usuário " + status + " com sucesso");
    }

    public List<ClientSearchResponseDTO> findClientsSearch(String search, User authenticatedUser) {

        Specification<User> spec = Specification
                .where(UserSpecification.companyContains(authenticatedUser.getCompany()))
                .and(UserSpecification.searchClients(search));


        return userRepository.findAll(spec)
                .stream()
                .map(userMapper::toClientsResponseDTO)
                .toList();
    }

    public UserLoggedResponseDTO getUserMe(User authenticatedUser) {
        User user = findUserByEmail(authenticatedUser.getEmail());
        return userMapper.toUserLoggedResponseDTO(user);
    }

    public void updateUserMe(User authenticatedUser,UpdateUserRequestDTO dto) {
        log.info("Inicinado atualização do usuário logado: email: {}", authenticatedUser.getEmail());
        User user = findUserByEmail(authenticatedUser.getEmail());
        userMapper.toUpdateMe(dto, user);
        userRepository.save(user);
        updateUserInKeycloak(authenticatedUser.getKeycloakId(), dto);
        log.info("O usuário logado com o email {} foi atualizado com sucesso", user.getEmail());
    }

    public void updateUserInKeycloak(UUID keycloakId, UpdateUserRequestDTO dto) {
        log.info("Iniciando a atualização do usuário com o keycloakId: {} no Keycloak", keycloakId);
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setFirstName(dto.name());
        keycloakUser.setLastName(dto.lastName());
        keycloak.realm(realm).users().get(keycloakId.toString()).update(keycloakUser);
        log.info("Usuário atualizado com sucesso no keycloak");
    }

}
