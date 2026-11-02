package com.example.demo.Dto;

import lombok.Data;

@Data
public class UserDto {
    private Long Id;
    private String email;
    private String username;
    private String password;
}
