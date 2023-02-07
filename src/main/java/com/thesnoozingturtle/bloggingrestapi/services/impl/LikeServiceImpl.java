package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;
import com.thesnoozingturtle.bloggingrestapi.payloads.UserDto;
import com.thesnoozingturtle.bloggingrestapi.repositories.PostRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.UserRepo;
import com.thesnoozingturtle.bloggingrestapi.services.LikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public LikeServiceImpl(UserRepo userRepo, PostRepo postRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void updateLikeOnPost(int userId, int postId) {
        User user = getUser(userId);
        Post post = getPost(postId);

        if(!user.getLikedPosts().contains(post)) {
            user.getLikedPosts().add(post);
            post.getLikedBy().add(user);
            post.setLikesCount(post.getLikesCount() + 1);
        } else {
            user.getLikedPosts().remove(post);
            post.getLikedBy().remove(user);
            post.setLikesCount(post.getLikesCount() - 1);
        }
        userRepo.save(user);
        postRepo.save(post);
    }

    private User getUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow((() -> new ResourceNotFoundException("User",
                "Id", userId)));
        return user;
    }

    private Post getPost(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return post;
    }
}
