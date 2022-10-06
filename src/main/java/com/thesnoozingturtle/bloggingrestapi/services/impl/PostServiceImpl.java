package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostResponse;
import com.thesnoozingturtle.bloggingrestapi.repositories.CategoryRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.PostRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.UserRepo;
import com.thesnoozingturtle.bloggingrestapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postPage = this.postRepo.findAll(pageable);
        return getPostResponse(postPage);
    }

    @Override
    public PostResponse getPostsByCategory(int categoryId, int pageNumber, int pageSize) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = this.postRepo.findAllByCategory(category, pageable);
        return getPostResponse(postPage);
    }

    @Override
    public PostResponse getPostsByUser(int userId, int pageNumber, int pageSize) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = this.postRepo.findAllByUser(user, pageable);
        return getPostResponse(postPage);
    }

    @Override
    public List<PostDto> searchPosts(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = this.postRepo.findByTitleContaining(keyword, pageable);
        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(this.modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }

    private PostResponse getPostResponse(Page<Post> postPage) {
        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            postDtos.add(this.modelMapper.map(post, PostDto.class));
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setNumberOfElementsOnSinglePage(postPage.getNumberOfElements());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }
}
