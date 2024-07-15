package com.joel.blog.service;

import com.joel.blog.dto.UserDto;
import com.joel.blog.dto.UserResponse;
import com.joel.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long userId);
    UserDto getUserById(Long userId);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy);
    void deleteUserById(Long userId);

    Optional<User> findByUserEmail(String email);
}
