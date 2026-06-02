package com.saj.api.modules.users.controller;

import com.saj.api.modules.users.controller.dtos.CreateUserDTO;
import com.saj.api.modules.users.controller.dtos.UpdateUserDTO;
import com.saj.api.modules.users.controller.dtos.UserSearchDTO;
import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.modules.users.docs.UserControllerDocs;
import com.saj.api.modules.users.service.UserService;
import com.saj.api.shared.dto.PaginationResponseDTO;
import com.saj.api.shared.dto.SuccessResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<PaginationResponseDTO<UsersResponseDTO>> findAllUsers(
            @ModelAttribute UserSearchDTO filter
            ) {
        return ResponseEntity.ok(userService.findAllUsersSearch(filter));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        this.userService.updateUser(id, updateUserDTO);
        return ResponseEntity.ok(SuccessResponseDTO.of("Usuário atualizado com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> findUserById(@PathVariable UUID id) {
        var user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SuccessResponseDTO> toggleUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.toggleUserActive(id));
    }
}
