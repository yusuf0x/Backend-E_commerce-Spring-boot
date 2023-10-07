package com.ecommerce.app.exceptions;

import java.io.Serial;

public class BrandNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public BrandNotFoundException(String message){
        super(message);
    }
}
