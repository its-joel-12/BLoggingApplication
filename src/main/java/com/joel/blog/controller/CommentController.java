package com.joel.blog.controller;

import com.joel.blog.dto.ApiResponse;
import com.joel.blog.dto.CommentDto;
import com.joel.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blog-app/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // CREATE COMMENT
    @PostMapping("post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId, @PathVariable Long userId) {
        return new ResponseEntity<>(commentService.createComment(commentDto, postId, userId), HttpStatus.CREATED);
    }

    // DELETE COMMENT
    @DeleteMapping("{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted Successfully", true), HttpStatus.OK);
    }

    // GET ALL COMMENTS
    @GetMapping()
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }
}
