package com.joel.blog.repository;

import com.joel.blog.model.Category;
import com.joel.blog.model.Post;
import com.joel.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByPostTitleContaining(String keyword);
}
