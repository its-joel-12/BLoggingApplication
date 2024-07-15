package com.joel.blog.service.impl;

import com.joel.blog.dto.CommentDto;
import com.joel.blog.exception.ResourceNotFoundException;
import com.joel.blog.model.Post;
import com.joel.blog.model.User;
import com.joel.blog.model.Comment;
import com.joel.blog.repository.CommentRepository;
import com.joel.blog.repository.PostRepository;
import com.joel.blog.repository.UserRepository;
import com.joel.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found with the given ID: " + postId));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found with the given ID: " + userId));

        Comment comment = modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepo.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment Not Found with the given ID: " + commentId));
        commentRepo.delete(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> allComments = commentRepo.findAll();

        if (!allComments.isEmpty()) {
            List<CommentDto> allCommDtos = allComments
                    .stream()
                    .map(c -> modelMapper.map(c, CommentDto.class))
                    .collect(Collectors.toList());
            return allCommDtos;
        }
        else {
            throw new ResourceNotFoundException("Either this page has no comments OR there are No comments in the database !");
        }
    }
}
