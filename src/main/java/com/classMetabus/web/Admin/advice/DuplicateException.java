package com.classMetabus.web.Admin.advice;

public class DuplicateException extends IllegalStateException{
    public DuplicateException(String message){
        super(message);
    }
}