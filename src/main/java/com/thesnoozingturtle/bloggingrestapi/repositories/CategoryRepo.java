package com.thesnoozingturtle.bloggingrestapi.repositories;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryId(UUID categoryId);
}
