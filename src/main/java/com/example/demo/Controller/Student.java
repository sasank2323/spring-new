package com.example.demo.Controller;

import com.example.demo.Service.StudentService;
import jakarta.validation.Valid;
import com.example.demo.Dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Optional;


@RestController
@RequestMapping("emp")
public class Student {

    private  StudentService studentService;

    Student(StudentService studentService)
    {
        this.studentService=studentService;
    }


    @GetMapping("/{emp_id}")
    public ResponseEntity<?> getemp(@PathVariable Long emp_id)
    {
        Optional<EmployeeDto> employeeDto = studentService.getStudentName(emp_id);
        if(employeeDto.isPresent())
        {
            return ResponseEntity.ok(employeeDto.get());
        }
        else
        {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createemp(@RequestBody @Valid EmployeeDto employeeDto)
    {
        EmployeeDto createdEmployee = studentService.storeEmployee(employeeDto);
        return ResponseEntity.ok(createdEmployee);
    }

    @DeleteMapping("/{emp_id}")
    public ResponseEntity<?> deleteemp(@PathVariable Long emp_id)
    {
        EmployeeDto ss=studentService.deleteEmployee(emp_id);
        return ResponseEntity.ok(ss);
    }

    @PutMapping("/{emp_id}")
    public ResponseEntity<EmployeeDto> updateemp(@PathVariable Long emp_id, @RequestBody @Valid EmployeeDto employeeDto)
    {
           // First, check if the employee exists
            Optional<EmployeeDto> existingEmployee = studentService.getStudentName(emp_id);
            if (existingEmployee.isPresent()) {
                // Update the employee details
                EmployeeDto updatedEmployee = studentService.storeEmployee(employeeDto);
                return ResponseEntity.ok(updatedEmployee);
            } else {
                return ResponseEntity.status(404).body(null);
            }
    }
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeDto> employees = studentService.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("No employees found");
        }
        return ResponseEntity.ok(employees);
    }

}
