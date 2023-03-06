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
import com.thesnoozingturtle.bloggingrestapi.security.JwtTokenHelper;
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
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    public PostServiceImpl(PostRepo postRepo, UserRepo userRepo, CategoryRepo categoryRepo, ModelMapper modelMapper, JwtTokenHelper jwtTokenHelper) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    public PostDto createPost(PostDto postDto, String userId, String categoryId, String token) {
        User user = getUser(userId);
        String email = jwtTokenHelper.getUsernameFromToken(token.substring(7));
        Category category = this.categoryRepo.findByCategoryId(UUID.fromString(categoryId)).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, String postId, String userId) {
        User user = getUser(userId);
        Post post = getPost(postId, user);
        String categoryId = postDto.getCategory().getCategoryId().toString();
        Category category = getCategory(categoryId);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(category);
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }



    @Override
    public void deletePost(String postId, String userId) {
        User user = getUser(userId);
        Post post = getPost(postId, user);;
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(String postId) {
        Post post = this.postRepo
                .findByPostId(UUID.fromString(postId))
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
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
    public PostResponse getPostsByCategory(String categoryId, int pageNumber, int pageSize) {
        Category category = getCategory(categoryId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = this.postRepo.findAllByCategory(category, pageable);
        return getPostResponse(postPage);
    }

    @Override
    public PostResponse getPostsByUser(String userId, int pageNumber, int pageSize) {
        User user = this.userRepo.findById(UUID.fromString(userId)).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
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
    private User getUser(String userId) {
        User user = this.userRepo.findById(UUID.fromString(userId)).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        return user;
    }
    private Post getPost(String postId, User user) {
        Post post = this.postRepo.findByUserAndPostId(user, UUID.fromString(postId)).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return post;
    }
    private Category getCategory(String categoryId) {
        Category category = categoryRepo.findByCategoryId(UUID.fromString(categoryId)).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        return category;
    }
}
