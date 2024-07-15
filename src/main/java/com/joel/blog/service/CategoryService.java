package com.joel.blog.service;

import com.joel.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
    void deleteCategory(Long categoryId);
    CategoryDto getCategoryById(Long categoryId);
    List<CategoryDto> getAllCategories();

}
