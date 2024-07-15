package com.joel.blog.controller;

import com.joel.blog.config.AppConstants;
import com.joel.blog.dto.ApiResponse;
import com.joel.blog.dto.UserDto;
import com.joel.blog.dto.UserResponse;
import com.joel.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blog-app/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // POST - CREATE USER
    @PostMapping()
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    // PUT - UPDATE USER
    @PutMapping("{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.updateUser(userDto, userId), HttpStatus.OK);
    }

    // DELETE - DELETE USER
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

    // GET - GET ALL USERS
    @GetMapping()
    public ResponseEntity<UserResponse> getAllUsers(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy) {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber, pageSize, sortBy), HttpStatus.OK);
    }

    // GET - GET USER BY ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }
}
