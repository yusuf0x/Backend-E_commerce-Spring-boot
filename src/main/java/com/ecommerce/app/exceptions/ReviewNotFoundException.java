package com.ecommerce.app.exceptions;

import java.io.Serial;

public class ReviewNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public ReviewNotFoundException(String message){
        super(message);
    }
}
