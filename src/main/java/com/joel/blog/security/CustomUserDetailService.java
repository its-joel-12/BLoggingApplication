package com.joel.blog.security;

import com.joel.blog.exception.ResourceNotFoundException;
import com.joel.blog.model.User;
import com.joel.blog.model.UserPrincipal;
import com.joel.blog.repository.UserRepository;
import com.joel.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepo.findByUserEmail(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found with the given email: " + username));

        User user = userService.findByUserEmail(username).orElse(null);

        if(user == null){
            throw new ResourceNotFoundException("User Not Found with the given email: " + username);
        }else{
        return new UserPrincipal(user);

        }
    }
}
