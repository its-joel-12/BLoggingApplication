package com.joel.blog.service.impl;

import com.joel.blog.config.AppConstants;
import com.joel.blog.dto.CategoryDto;
import com.joel.blog.dto.UserDto;
import com.joel.blog.dto.UserResponse;
import com.joel.blog.exception.ResourceNotFoundException;
import com.joel.blog.model.Role;
import com.joel.blog.model.User;
import com.joel.blog.repository.RoleRepository;
import com.joel.blog.repository.UserRepository;
import com.joel.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepos;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // CREATE USER
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);

        Role role = roleRepos.findById(AppConstants.ROLE_NORMAL).get();
        System.out.println("role: " + role.getRoleName());

//        user.setRoles(new Role());

        if (user.getRoles()==null) {
            Set<Role> rolesBlank = new HashSet<>();
            user.setRoles(rolesBlank);
        }

        user.getRoles().forEach(System.out::println);
        user.getRoles().add(role);
//        user.getRoles()

        // bcrypt implementation
        user.setUserPassword(encoder.encode(user.getUserPassword()));

        User savedUser = userRepository.save(user);
        return userToDto(savedUser);
    }

    // UPDATE USER
    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUserName(userDto.getUserName());
            user.setUserEmail(userDto.getUserEmail());
            user.setUserPassword(userDto.getUserPassword());
            user.setUserAbout(userDto.getUserAbout());

            // bcrypt implementation
            user.setUserPassword(encoder.encode(user.getUserPassword()));

            User updatedUser = userRepository.save(user);

            return userToDto(updatedUser);
        } else {
            throw new ResourceNotFoundException("User Not Found with the given ID: " + userId);
        }
    }

    // GET USER BY ID
    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return userToDto(user);
        } else {
            throw new ResourceNotFoundException("User Not Found with the given ID: " + userId);
        }
    }

    // GET ALL USERS
    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        Page<User> allPageUsers = userRepository.findAll(pageable);
        List<User> allUsers = allPageUsers.getContent();


        if (!allUsers.isEmpty()) {
            List<UserDto> allUserDtos = allUsers.stream().map(user -> userToDto(user)).collect(Collectors.toList());

            UserResponse userResponse = new UserResponse();
            userResponse.setContent(allUserDtos);
            userResponse.setPageNumber(allPageUsers.getNumber());
            userResponse.setPageSize(allPageUsers.getSize());
            userResponse.setTotalElements(allPageUsers.getTotalElements());
            userResponse.setTotalPages(allPageUsers.getTotalPages());
            userResponse.setIsLastPage(allPageUsers.isLast());

            return userResponse;
        } else {
            throw new ResourceNotFoundException("Either this page has no users OR there are No Users in the database !");
        }
    }

    // DELETE USER
    @Override
    public void deleteUserById(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException("User Not Found with the given ID: " + userId);
        }
    }


    private User dtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
//        user.setUserId(userDto.getUserId());
//        user.setUserName(userDto.getUserName());
//        user.setUserEmail(userDto.getUserEmail());
//        user.setUserPassword(userDto.getUserPassword());
//        user.setUserAbout(userDto.getUserAbout());
        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
//        userDto.setUserId(user.getUserId());
//        userDto.setUserName(user.getUserName());
//        userDto.setUserEmail(user.getUserEmail());
//        userDto.setUserPassword(user.getUserPassword());
//        userDto.setUserAbout(user.getUserAbout());
        return userDto;
    }


    @Override
    public Optional<User> findByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }
}
