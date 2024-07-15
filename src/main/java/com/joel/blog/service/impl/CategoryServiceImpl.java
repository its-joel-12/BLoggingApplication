package com.joel.blog.service.impl;

import com.joel.blog.dto.CategoryDto;
import com.joel.blog.exception.ResourceNotFoundException;
import com.joel.blog.model.Category;
import com.joel.blog.repository.CategoryRepository;
import com.joel.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            category.setCategoryTitle(categoryDto.getCategoryTitle());
            category.setCategoryDescription(categoryDto.getCategoryDescription());

            Category updatedCategory = categoryRepository.save(category);

            return modelMapper.map(updatedCategory, CategoryDto.class);
        } else {
            throw new ResourceNotFoundException("Category Not Found with the given ID: " + categoryId);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new ResourceNotFoundException("Category Not Found with the given ID: " + categoryId);
        }
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            return modelMapper.map(category, CategoryDto.class);
        } else {
            throw new ResourceNotFoundException("Category Not Found with the given ID: " + categoryId);
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        if (!allCategories.isEmpty()) {
            List<CategoryDto> categroiesDtos = allCategories
                    .stream()
                    .map(c -> modelMapper.map(c, CategoryDto.class))
                    .collect(Collectors.toList());
            return categroiesDtos;
        }
        else{
            throw new ResourceNotFoundException("No Categories found in the database !");
        }
    }
}
