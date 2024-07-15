package com.joel.blog.dto;

import com.joel.blog.model.Post;
import com.joel.blog.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long commentId;

    private String commentContent;

//    private PostDto postDto;

//    private UserDto userDto;
}
