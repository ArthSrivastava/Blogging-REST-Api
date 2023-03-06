package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.entities.Comment;
import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.payloads.CommentDto;
import com.thesnoozingturtle.bloggingrestapi.repositories.CommentRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.PostRepo;
import com.thesnoozingturtle.bloggingrestapi.repositories.UserRepo;
import com.thesnoozingturtle.bloggingrestapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, String postId, String userId) {
        Post post = getPost(postId);
        User user = getUser(userId);

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }


    @Override
    public void deleteComment(String commentId) {
        Comment comment = getComment(commentId);
        this.commentRepo.delete(comment);

    }

    private Comment getComment(String commentId) {
        Comment comment = this.commentRepo.findByCommentId(UUID.fromString(commentId)).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
        return comment;
    }

    private User getUser(String userId) {
        User user = this.userRepo.findById(UUID.fromString(userId)).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        return user;
    }

    private Post getPost(String postId) {
        Post post = this.postRepo.findByPostId(UUID.fromString(postId)).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return post;
    }
}
