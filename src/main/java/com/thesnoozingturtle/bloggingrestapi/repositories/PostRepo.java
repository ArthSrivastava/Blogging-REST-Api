package com.thesnoozingturtle.bloggingrestapi.repositories;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface PostRepo extends JpaRepository<Post, UUID> {

     Page<Post> findAllByUser(User user, Pageable pageable);
     Page<Post> findAllByCategory(Category category, Pageable pageable);
     Page<Post> findByTitleContaining(String keyword, Pageable pageable);
     Optional<Post> findByUserAndPostId(User user, UUID postId);
     Optional<Post> findByPostId(UUID postId);

}
