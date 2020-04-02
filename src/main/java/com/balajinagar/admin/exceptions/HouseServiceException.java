package com.balajinagar.admin.exceptions;

public class HouseServiceException extends RuntimeException {
    private static final long serialVersionUID = 6012045L;

    public HouseServiceException(String message){
        super(message);
    }
}
