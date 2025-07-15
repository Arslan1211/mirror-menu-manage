package com.example.demo.exeption.handler;

import com.example.demo.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDishNotFoundException(
            DishNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(
            UsernameNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedEditException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedEditException(
            UnauthorizedEditException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExistsException(
            UsernameExistsException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiError> handleValidationExceptions(
            Exception ex, WebRequest request) {

        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setPath(request.getDescription(false).replace("uri=", ""));

        Map<String, String> fieldErrors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException validationEx) {
            apiError.setMessage("Validation error");
            validationEx.getBindingResult().getFieldErrors().forEach(error -> {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            });
        } else if (ex instanceof HttpMessageNotReadableException) {
            apiError.setMessage("Invalid JSON data");

            fieldErrors.put("category", "Invalid category value");
        }

        apiError.setFieldErrors(fieldErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            WebRequest request) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage(String.format(
                "Method '%s' is not supported for this endpoint",
                ex.getMethod()
        ));
        apiError.setPath(request.getDescription(false).replace("uri=", ""));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("httpMethod", "Unsupported HTTP method");
        apiError.setFieldErrors(fieldErrors);

        return new ResponseEntity<>(apiError, status);
    }
}
