package com.smartiq.smartiqtask.ExceptionHandler;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
