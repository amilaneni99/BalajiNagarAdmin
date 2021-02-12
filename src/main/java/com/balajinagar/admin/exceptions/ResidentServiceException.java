package com.balajinagar.admin.exceptions;

public class ResidentServiceException extends RuntimeException {
    private static final long serialVersionUID = 6012046L;

    public ResidentServiceException(String message){
        super(message);
    }
}
