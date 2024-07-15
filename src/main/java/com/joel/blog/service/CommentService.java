package com.joel.blog.service;

import com.joel.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId, Long userId);
    void deleteComment(Long commentId);
    List<CommentDto> getAllComments();
}
