package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.config.AppConstants;
import com.thesnoozingturtle.bloggingrestapi.entities.Role;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.payloads.UserDto;
import com.thesnoozingturtle.bloggingrestapi.repositories.RoleRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.UserRepo;
import com.thesnoozingturtle.bloggingrestapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User registeredUser = this.userRepo.save(user);
        return userToUserDto(registeredUser);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User storedUser = getUser(userId);
        storedUser.setName(userDto.getName());
        storedUser.setPassword(userDto.getPassword());
        storedUser.setEmail(userDto.getEmail());
        storedUser.setAbout(userDto.getAbout());
        User updatedUser = this.userRepo.save(storedUser);
        return this.userToUserDto(updatedUser);
    }


    @Override
    public UserDto getUserById(String userId) {
        User user = getUser(userId);
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
    public void deleteUser(String userId) {
        User user = getUser(userId);
        user.setRoles(null);
        this.userRepo.delete(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepo
                .findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist!", username));
        return userToUserDto(user);
    }

    private User userDtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
//        user.setId(userDto.getId());
//        user.setAbout(userDto.getAbout());
//        user.setEmail(userDto.getEmail());
//        user.setName(userDto.getName());
//        user.setPassword(userDto.getPassword());
        return user;
    }
    private UserDto userToUserDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
    private User getUser(String userId) {
        User storedUser = this.userRepo.findById(UUID.fromString(userId)).orElseThrow((() -> new ResourceNotFoundException("User",
                "Id", userId)));
        return storedUser;
    }
}
