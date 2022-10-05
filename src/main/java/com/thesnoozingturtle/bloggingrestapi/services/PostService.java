package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostResponse;

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
    PostResponse getAllPosts(int pageNumber, int pageSize);

    //get all posts by category
    PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize);

    //get all posts by user
    PostResponse getPostsByUser(int userId, int pageNumber, int pageSize);

    //search posts
    List<PostDto> searchPosts(String keyword);
}
