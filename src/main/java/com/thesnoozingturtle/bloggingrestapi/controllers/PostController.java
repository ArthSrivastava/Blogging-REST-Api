package com.thesnoozingturtle.bloggingrestapi.controllers;

import com.thesnoozingturtle.bloggingrestapi.payloads.ApiResponse;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostResponse;
import com.thesnoozingturtle.bloggingrestapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable int userId, @PathVariable int categoryId) {
        PostDto post = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //get post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable int categoryId,
                                                           @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {
        PostResponse postResponse = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable int userId,
                                                       @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {
        PostResponse postResponse = this.postService.getPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize) {
        PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    //get single post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully!", true), HttpStatus.OK);
    }

    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> deletePost(@RequestBody PostDto postDto, @PathVariable int postId) {
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
}
