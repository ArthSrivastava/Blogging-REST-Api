package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, int postId, int userId);
    void deleteComment(int commentId);
}
