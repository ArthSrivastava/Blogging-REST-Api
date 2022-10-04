package com.thesnoozingturtle.bloggingrestapi.controllers;

import com.thesnoozingturtle.bloggingrestapi.payloads.ApiResponse;
import com.thesnoozingturtle.bloggingrestapi.payloads.UserDto;
import com.thesnoozingturtle.bloggingrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    //GET: Get the user/users
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    //POST: Create new user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = this.userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //PUT: Update a user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
        UserDto userDto1 = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(userDto1);
    }

    //DELETE: Delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully!", true), HttpStatus.OK);
    }
}
