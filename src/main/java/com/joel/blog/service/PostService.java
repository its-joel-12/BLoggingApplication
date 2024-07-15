package com.joel.blog.service;

import com.joel.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Long userId, Long categoryId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long postId);

    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto> getPostsByUser(Long userId);

    List<PostDto> getPostsByKeyword(String keyword);
}
