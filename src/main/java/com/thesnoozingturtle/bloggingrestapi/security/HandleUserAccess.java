package com.thesnoozingturtle.bloggingrestapi.security;

import com.thesnoozingturtle.bloggingrestapi.entities.User;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("handleUserAccess")
public class HandleUserAccess {

    private final UserRepo userRepo;

    @Autowired
    public HandleUserAccess(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean handle(String userId, Authentication authentication) {
        User user = userRepo.findById(UUID.fromString(userId)).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return user.getEmail().equals(authentication.getName());
    }
}
