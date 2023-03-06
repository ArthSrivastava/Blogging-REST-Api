package com.thesnoozingturtle.bloggingrestapi.repositories;

import com.thesnoozingturtle.bloggingrestapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {
    Optional<Comment> findByCommentId(UUID commentId);
}
