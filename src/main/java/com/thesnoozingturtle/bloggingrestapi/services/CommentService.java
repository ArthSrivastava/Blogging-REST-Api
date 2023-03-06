package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, String postId, String userId);
    void deleteComment(String commentId);
}
