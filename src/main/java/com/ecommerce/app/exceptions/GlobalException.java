package com.ecommerce.app.exceptions;

import com.ecommerce.app.payload.response.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ErrorObject> handleBrandNotFoundException(
            BrandNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCartNotFoundException(
            CartNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<ErrorObject> handleSubCategoryNotFoundException(
            SubCategoryNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorObject> handleReviewNotFoundException(
            ReviewNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePersonNotFoundException(
            PersonNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorObject> handleOrderNotFoundException(
            OrderNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCouponNotFoundException(
            CouponNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorObject> handleProductNotFoundException(
            ProductNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<ErrorObject> handleFavoriteNotFoundException(
            FavoriteNotFoundException ex, WebRequest request
    ){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp( LocalDateTime.now());
        return new ResponseEntity<>(
                errorObject, HttpStatus.NOT_FOUND
        );
    }
}
