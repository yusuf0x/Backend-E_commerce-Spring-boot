package com.ecommerce.app.exceptions;

import java.io.Serial;

public class FavoriteNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public FavoriteNotFoundException(String message){
        super(message);
    }
}
