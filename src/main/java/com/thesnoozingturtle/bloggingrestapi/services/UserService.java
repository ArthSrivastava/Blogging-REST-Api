package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, String userId);

    UserDto getUserById(String userId);

    List<UserDto> getAllUsers();

    void deleteUser(String userId);

    UserDto getUserByUsername(String username);
}
