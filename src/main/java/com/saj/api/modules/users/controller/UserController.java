package com.saj.api.modules.users.controller;

import com.saj.api.modules.users.controller.dtos.CreateUserDTO;
import com.saj.api.modules.users.controller.dtos.UserSearchDTO;
import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.modules.users.docs.UserControllerDocs;
import com.saj.api.modules.users.service.UserService;
import com.saj.api.shared.dto.PagenationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<PagenationResponseDTO<UsersResponseDTO>> findAllUsers(
            @ModelAttribute UserSearchDTO filter
            ) {
        return ResponseEntity.ok(userService.findAllUsersSearch(filter));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
