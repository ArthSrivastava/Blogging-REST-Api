package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    //create posts
    PostDto createPost(PostDto postDto, String userId, String categoryId, String token);

    //update
    PostDto updatePost(PostDto postDto, String postId, String userId);

    //delete
    void deletePost(String postId, String userId);

    //get post
    PostDto getPostById(String postId);

    //get all posts
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortOrder);

    //get all posts by category
    PostResponse getPostsByCategory(String categoryId, int pageNumber, int pageSize);

    //get all posts by user
    PostResponse getPostsByUser(String userId, int pageNumber, int pageSize);

    //search posts
    List<PostDto> searchPosts(String keyword, int pageNumber, int pageSize);
}
