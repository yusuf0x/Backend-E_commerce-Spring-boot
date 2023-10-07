package com.ecommerce.app.exceptions;

import java.io.Serial;

public class CartItemNotFoundException  extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public CartItemNotFoundException(String message){
        super(message);
    }
}
