package com.ecommerce.app.exceptions;

import java.io.Serial;

public class SubCategoryNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public SubCategoryNotFoundException(String message){
        super(message);
    }
}
