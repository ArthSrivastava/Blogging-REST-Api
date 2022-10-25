package com.thesnoozingturtle.bloggingrestapi.repositories;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import com.thesnoozingturtle.bloggingrestapi.entities.Post;
import com.thesnoozingturtle.bloggingrestapi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepo extends JpaRepository<Post, Integer> {

    public Page<Post> findAllByUser(User user, Pageable pageable);
    public Page<Post> findAllByCategory(Category category, Pageable pageable);
    public Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
