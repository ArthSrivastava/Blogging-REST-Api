package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;

import java.util.List;

public interface PostService {

    //create posts
    PostDto createPost(PostDto postDto, int userId, int categoryId);

    //update
    PostDto updatePost(PostDto postDto, int postId);

    //delete
    void deletePost(int postId);

    //get post
    PostDto getPostById(int postId);

    //get all posts
    List<PostDto> getAllPosts();

    //get all posts by category
    List<PostDto> getPostsByCategory(int categoryId);

    //get all posts by user
    List<PostDto> getPostsByUser(int userId);

    //search posts
    List<PostDto> searchPosts(String keyword);
}
