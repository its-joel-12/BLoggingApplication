package com.joel.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BloggingAppExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<Object> handleException(Exception ex) {
        // RESOURCE NOT FOUND EXCEPTION
        if (ex instanceof ResourceNotFoundException) {
            BloggingAppException error = new BloggingAppException(
                    404,
                    HttpStatus.NOT_FOUND,
                    ex.getMessage(),
                    "Required Resource Not Found, hence the operation was unsuccessful!!"
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // IN VALID FIELD DATA
        if (ex instanceof MethodArgumentNotValidException) {
            Map<String, String> res = new HashMap<>();

            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach((er) -> {
                String fieldName = ((FieldError) er).getField();
                String message = er.getDefaultMessage();
                res.put(fieldName, message);
            });
            BloggingAppException error = new BloggingAppException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Invalid Inputs !!",
                    res.toString()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // MISSING REQUEST BODY - HttpMessageNotReadableException
        else if (ex instanceof HttpMessageNotReadableException) {
            BloggingAppException error = new BloggingAppException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Please make sure you send data in proper format, also don't send an empty request body!!",
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // NoResourceFoundException
        else if (ex instanceof NoResourceFoundException) {
            BloggingAppException error = new BloggingAppException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Check proper format of the api and its Http method!!",
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // MethodArgumentTypeMismatchException
        else if (ex instanceof MethodArgumentTypeMismatchException) {
            BloggingAppException error = new BloggingAppException(
                    400,
                    HttpStatus.BAD_REQUEST,
                    "Please provide proper Integer ID input in the api url argument!!",
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // InternalAuthenticationServiceException
        else if (ex instanceof InternalAuthenticationServiceException) {
            BloggingAppException error = new BloggingAppException(
                    401,
                    HttpStatus.UNAUTHORIZED,
                    ex.getMessage(),
                    "Please check your login credentials !!"
            );
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        // BadCredentialsException
        else if (ex instanceof BadCredentialsException) {
            BloggingAppException error = new BloggingAppException(
                    401,
                    HttpStatus.UNAUTHORIZED,
                    "Wrong password !!",
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
        // AccessDeniedException
        else if (ex instanceof AccessDeniedException) {
            BloggingAppException error = new BloggingAppException(
                    403,
                    HttpStatus.FORBIDDEN,
                    "You don't have permission to access this resource !!",
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        // UN IDENTIFIED EXCEPTION
        else {
            BloggingAppException error = new BloggingAppException(
                    500,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Un identified error | Please contact the Backend Developer!!... :D",
                    ex.getMessage()
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
