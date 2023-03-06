package com.thesnoozingturtle.bloggingrestapi.services.impl;

import com.thesnoozingturtle.bloggingrestapi.entities.Category;
import com.thesnoozingturtle.bloggingrestapi.exceptions.ResourceNotFoundException;
import com.thesnoozingturtle.bloggingrestapi.payloads.CategoryDto;
import com.thesnoozingturtle.bloggingrestapi.repositories.CategoryRepo;
import com.thesnoozingturtle.bloggingrestapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = getCategoryGeneral(categoryId);
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category savedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }


    @Override
    public void deleteCategory(String categoryId) {
        Category category = getCategoryGeneral(categoryId);
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        Category category = getCategoryGeneral(categoryId);
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(this.modelMapper.map(category, CategoryDto.class));
        }
        return categoryDtos;
    }
    private Category getCategoryGeneral(String categoryId) {
        Category category = this.categoryRepo.findByCategoryId(UUID.fromString(categoryId)).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        return category;
    }
}
