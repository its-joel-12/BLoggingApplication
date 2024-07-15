package com.joel.blog.controller;

import com.joel.blog.dto.ApiResponse;
import com.joel.blog.dto.PostDto;
import com.joel.blog.service.FileService;
import com.joel.blog.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("blog-app/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // POST - CREATE POST WITH UID & CID
    @PostMapping("user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId, @PathVariable Long categoryId) {
        return new ResponseEntity<>(postService.createPost(postDto, userId, categoryId), HttpStatus.CREATED);
    }

    // UPDATE POST
    @PutMapping("{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) {
        return new ResponseEntity<>(postService.updatePost(postDto, postId), HttpStatus.OK);
    }

    // DELETE POST
    @DeleteMapping("{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    // GET ALL POSTS
    @GetMapping()
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    // GET POSTS BY ID
    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    // GET POSTS BY USER
    @GetMapping("user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(postService.getPostsByUser(userId), HttpStatus.OK);
    }

    // GET POSTS BY CATEGORY
    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId) {
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
    }

    // GET POSTS BY KEYWORD SEARCH
    @GetMapping("keyword/{keyword}")
    public ResponseEntity<List<PostDto>> getPostsByKeyword(@PathVariable String keyword) {
        return new ResponseEntity<>(postService.getPostsByKeyword(keyword), HttpStatus.OK);
    }

    // POST - IMAGE UPLOAD
    @PostMapping("image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                         @PathVariable Long postId) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setPostImageName(fileName);
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // METHOD TO SERVE FILES
    @GetMapping(value = "image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}