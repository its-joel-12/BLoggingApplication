package com.joel.blog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long categoryId;

    @NotEmpty(message = "Category Title cannot be empty")
    @NotBlank
    @Size(min = 4)
    private String categoryTitle;

    @NotEmpty(message = "Category Description cannot be empty")
    @NotBlank
    @Size(min = 10)
    private String categoryDescription;
}
