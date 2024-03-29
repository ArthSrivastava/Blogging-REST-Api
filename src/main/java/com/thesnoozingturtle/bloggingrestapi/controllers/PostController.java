package com.thesnoozingturtle.bloggingrestapi.controllers;

import com.thesnoozingturtle.bloggingrestapi.config.AppConstants;
import com.thesnoozingturtle.bloggingrestapi.payloads.ApiResponse;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostDto;
import com.thesnoozingturtle.bloggingrestapi.payloads.PostResponse;
import com.thesnoozingturtle.bloggingrestapi.services.FileService;
import com.thesnoozingturtle.bloggingrestapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    @PreAuthorize(value = "@handleUserAccess.handle(#userId, authentication)")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable String userId, @PathVariable String categoryId,
                                              @RequestHeader("Authorization") String token) {
        PostDto post = postService.createPost(postDto, userId, categoryId, token);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //get post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable String categoryId,
                                                          @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize) {
        PostResponse postResponse = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //get post by user
    @GetMapping("/user/{userId}/posts")
    @PreAuthorize(value = "@handleUserAccess.handle(#userId, authentication)")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable String userId,
                                                      @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize) {
        PostResponse postResponse = this.postService.getPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
        PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    //get single post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable String postId) {
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/user/{userId}/posts/{postId}")
    @PreAuthorize(value = "@handleUserAccess.handle(#userId, authentication)")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable String postId,
                                                  @PathVariable String userId) {
        this.postService.deletePost(postId, userId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully!", true), HttpStatus.OK);
    }

    //update post
    @PutMapping("/user/{userId}/posts/{postId}")
    @PreAuthorize(value = "@handleUserAccess.handle(#userId, authentication)")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable String postId, @PathVariable String userId) {
        PostDto updatedPost = this.postService.updatePost(postDto, postId, userId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //search by title
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByTitle(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                                       @PathVariable String keyword) {
        List<PostDto> postDtos = this.postService.searchPosts(keyword, pageNumber, pageSize);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/posts/image/upload/{postId}")
    @PreAuthorize(value = "@handleUserAccess.handle(#userId, authentication)")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                   @PathVariable String postId, @PathVariable String userId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String uploadImageName = this.fileService.uploadImage(path, image);
        postDto.setImageName(uploadImageName);
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId, userId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    //produces: specifies which type of MIME media this can produce to the client
    @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream image = this.fileService.downloadImage(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(image, response.getOutputStream());
    }
}
