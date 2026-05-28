package com.saj.api.modules.users.service;

import com.saj.api.modules.users.controller.dtos.UsersResponseDTO;
import com.saj.api.modules.users.domain.entities.User;
import com.saj.api.modules.users.domain.mappers.UserMapper;
import com.saj.api.modules.users.infrastructure.repository.UserRepository;
import com.saj.api.shared.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
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

    public List<UsersResponseDTO> finsAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUsersResponseDTO)
                .toList();
    }


}
