package com.example.demo.Entity;

import jakarta.validation.constraints.Min;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;


    private String department;


}
