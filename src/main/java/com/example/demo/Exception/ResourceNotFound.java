package com.example.demo.Exception;

public class ResourceNotFound extends RuntimeException{
    public ResourceNotFound(String message)
    {
        super(message);
    }
}
