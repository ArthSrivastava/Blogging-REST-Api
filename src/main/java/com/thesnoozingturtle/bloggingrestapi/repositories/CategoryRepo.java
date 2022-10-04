package com.thesnoozingturtle.bloggingrestapi.repositories;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
