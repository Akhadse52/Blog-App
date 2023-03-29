package com.myblog3.exception;

import com.myblog3.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Special Exception handler
@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResponseNotFoundException(ResourceNotFoundException  exception, WebRequest webRequest){
        ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
        return  new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //Global Exception handler

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception exception , WebRequest webRequest){
    ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
   return  new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception, WebRequest webRequest){
    ErrorDetails errorDetails = new ErrorDetails(new Date() , exception.getMessage(),webRequest.getDescription(false));
    return  new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
}
}
