package com.joel.blog.dto;

import com.joel.blog.model.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long postId;

    @NotEmpty
    @NotBlank
    private String postTitle;

    @NotEmpty
    @NotBlank
    private String postContent;


    private String postImageName;
    private Date postAddedDate;
    private CategoryDto category;
    private UserDto user;

    private Set<CommentDto> comments;

}
