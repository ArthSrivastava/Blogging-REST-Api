package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;
import com.thesnoozingturtle.bloggingrestapi.repositories.CategoryRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.PostRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.UserRepo;
import com.thesnoozingturtle.bloggingrestapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = this.postRepo.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(this.modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = this.postRepo.findAllByCategory(category);
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(this.modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> posts = this.postRepo.findAllByUser(user);
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(this.modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return null;
    }
}
