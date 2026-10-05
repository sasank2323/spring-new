package com.example.demo.Advice;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(org.springframework.core.MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, org.springframework.core.MethodParameter returnType, org.springframework.http.MediaType selectedContentType, Class selectedConverterType, org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response) {
        if (body instanceof ApiResponse<?>) {
            return body;
        }
        return new ApiResponse<>(body);
    }

}
