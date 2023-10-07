package com.ecommerce.app.exceptions;

import java.io.Serial;

public class CouponNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1;

    public CouponNotFoundException(String message){
        super(message);
    }
}
