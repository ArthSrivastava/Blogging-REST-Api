package com.thesnoozingturtle.bloggingrestapi.services;

import com.thesnoozingturtle.bloggingrestapi.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //Create
    public CategoryDto createCategory(CategoryDto categoryDto);

    //Update
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    //Delete
    public void deleteCategory(int categoryId);

    //Get
    public CategoryDto getCategory(int categoryId);

    //Get all
    public List<CategoryDto> getAllCategories();
}
