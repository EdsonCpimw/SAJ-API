package com.saj.api.modules.users.controller;

import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.modules.users.docs.UserControllerDocs;
import com.saj.api.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UsersResponseDTO>> findAllUsers() {
        var litUsers = userService.finsAllUsers();
        return ResponseEntity.ok(litUsers);
    }

}
