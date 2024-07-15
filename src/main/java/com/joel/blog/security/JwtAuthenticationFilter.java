package com.joel.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. get token
        String requesToken = request.getHeader("Authorization");

        // Bearer eq1231jnk1jnkjn123.12j3n.12
        System.out.println("requesToken: " + requesToken);

        String username = null;
        String token = null;

        if (requesToken != null && requesToken.startsWith("Bearer")) {
            token = requesToken.substring(7);

            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                System.out.println("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                System.out.println("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Jwt token does not begin with 'Bearer'");
        }

        // once we get the token, now validate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // fetch user details from username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenHelper.validateToken(token, userDetails)) {
                // set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                System.out.println("Validation fails !");
            }
        } else {
            System.out.println("Username is null or context is not null !");

        }

        filterChain.doFilter(request, response);
    }
}
