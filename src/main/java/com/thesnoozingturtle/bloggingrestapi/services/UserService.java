package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    public UserDto createUser(UserDto user);

    public UserDto updateUser(UserDto user, Integer userId);

    public UserDto getUserById(Integer userId);

    public List<UserDto> getAllUsers();

    public void deleteUser(Integer userId);
}
