package com.example.demo.Dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @Max(value = 120, message = "Age must be less than or equal to 120")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;
    private String department;
}
