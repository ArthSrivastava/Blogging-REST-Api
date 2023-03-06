package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //Create
    public CategoryDto createCategory(CategoryDto categoryDto);

    //Update
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);

    //Delete
    public void deleteCategory(String categoryId);

    //Get
    public CategoryDto getCategory(String categoryId);

    //Get all
    public List<CategoryDto> getAllCategories();
}
