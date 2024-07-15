package com.joel.blog.service.impl;

import com.joel.blog.dto.PostDto;
import com.joel.blog.exception.ResourceNotFoundException;
import com.joel.blog.model.Category;
import com.joel.blog.model.Post;
import com.joel.blog.model.User;
import com.joel.blog.repository.CategoryRepository;
import com.joel.blog.repository.PostRepository;
import com.joel.blog.repository.UserRepository;
import com.joel.blog.service.CategoryService;
import com.joel.blog.service.PostService;
import com.joel.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    // CREATE POST
    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found with the given ID: " + userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found with the given ID: " + categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setPostImageName("default.png");
        post.setPostAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepo.save(post);

        return modelMapper.map(savedPost, PostDto.class);
    }

    // UPDATE POST
    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found with the given ID: " + postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImageName(postDto.getPostImageName());

        Post updatedPost = postRepo.save(post);

        return modelMapper.map(updatedPost, PostDto.class);
    }

    // DELETE POST
    @Override
    public void deletePost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found with the given ID: " + postId));
        postRepo.delete(post);
    }

    // GET ALL POSTS
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> allPosts = postRepo.findAll();

        if (!allPosts.isEmpty()) {
            List<PostDto> postDtos = allPosts
                    .stream()
                    .map(p -> modelMapper.map(p, PostDto.class))
                    .collect(Collectors.toList());
            return postDtos;
        } else {
            throw new ResourceNotFoundException("No Posts found in the database !");
        }
    }

    // GET POST BY ID
    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found with the given ID: " + postId));
        return modelMapper.map(post, PostDto.class);
    }

    // GET POSTS BY CATEGORY
    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found with the given ID: " + categoryId));

        List<Post> postsByCategory = postRepo.findByCategory(category);

        if (!postsByCategory.isEmpty()) {
            List<PostDto> postDtos = postsByCategory
                    .stream()
                    .map(p -> modelMapper.map(p, PostDto.class))
                    .collect(Collectors.toList());
            return postDtos;
        } else {
            throw new ResourceNotFoundException("No such posts exists related to the given category: " + categoryService.getCategoryById(categoryId).getCategoryTitle());
        }
    }

    // GET POSTS BY USER
    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found with the given ID: " + userId));

        List<Post> postsByUser = postRepo.findByUser(user);

        if (!postsByUser.isEmpty()) {
            List<PostDto> postDtos = postsByUser
                    .stream()
                    .map(p -> modelMapper.map(p, PostDto.class))
                    .collect(Collectors.toList());
            return postDtos;
        } else {
            throw new ResourceNotFoundException("No posts posted by : " + userService.getUserById(userId).getUserName());
        }
    }

    // GET POSTS BY KEYWORD SEARCH
    @Override
    public List<PostDto> getPostsByKeyword(String keyword) {
        List<Post> posts = postRepo.findByPostTitleContaining(keyword);

        if (!posts.isEmpty()) {
            List<PostDto> postDtos = posts
                    .stream()
                    .map(p -> modelMapper.map(p, PostDto.class))
                    .collect(Collectors.toList());
            return postDtos;
        } else {
            throw new ResourceNotFoundException("No results found");
        }
    }

}