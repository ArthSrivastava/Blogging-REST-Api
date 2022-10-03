package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.entities.User;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.payloads.UserDto;
import com.thesnoozingturtle.bloggingrestapi.repositories.UserRepo;
import com.thesnoozingturtle.bloggingrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User storedUser = this.userRepo.findById(userId).orElseThrow((() -> new ResourceNotFoundException("User",
                "Id", userId)));
        storedUser.setName(userDto.getName());
        storedUser.setPassword(userDto.getPassword());
        storedUser.setEmail(userDto.getEmail());
        storedUser.setAbout(userDto.getAbout());
        User updatedUser = this.userRepo.save(storedUser);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow((() -> new ResourceNotFoundException("User",
                "Id", userId)));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = this.userRepo.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : allUsers) {
            userDtos.add(this.userToUserDto(user));
        }
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow((() -> new ResourceNotFoundException("User",
                "Id", userId)));
        this.userRepo.delete(user);
    }

    private User userDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAbout(user.getAbout());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
