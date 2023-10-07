package com.ecommerce.app.exceptions;

import java.io.Serial;

public class CartNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public CartNotFoundException(String message){
        super(message);
    }
}
