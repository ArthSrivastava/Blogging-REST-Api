package com.thesnoozingturtle.bloggingrestapi.controllers;

import com.thesnoozingturtle.bloggingrestapi.payloads.ApiResponse;
import com.thesnoozingturtle.bloggingrestapi.services.LikeService;
import com.thesnoozingturtle.bloggingrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }


    @PostMapping("/user/{userId}/post/{postId}/likes")
    public ResponseEntity<ApiResponse> likePost(@PathVariable String userId, @PathVariable String postId) {
        likeService.updateLikeOnPost(userId, postId);
        ApiResponse apiResponse = new ApiResponse("Post like successfully updated by user with user id " + userId, true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
