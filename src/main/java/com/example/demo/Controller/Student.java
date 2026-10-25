package com.example.demo.Controller;

import com.example.demo.Advice.ApiResponse;
import com.example.demo.Service.StudentService;
import jakarta.validation.Valid;
import com.example.demo.Dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Exception.ResourceNotFound;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("emp")
public class Student {

    private  StudentService studentService;
    private RestClient restClient;
//    private Logger log = LoggerFactory.getLogger(Student.class);

    Student(StudentService studentService, RestClient restClient)
    {
        this.studentService=studentService;
        this.restClient=restClient;
    }


    @GetMapping("/{emp_id}")
    public ResponseEntity<?> getemp(@PathVariable Long emp_id)
    {
        Optional<EmployeeDto> employeeDto = studentService.getStudentName(emp_id);
        log.info("Retrieved employee with ID: {}", emp_id);
        log.debug("Employee details: {}", employeeDto);
        return ResponseEntity.ok(employeeDto.get());
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createemp(@RequestBody @Valid EmployeeDto employeeDto)
    {
        EmployeeDto createdEmployee = studentService.storeEmployee(employeeDto);
        log.info("Created employee with ID: {}", createdEmployee.getId());
        return ResponseEntity.ok(createdEmployee);
    }

    @DeleteMapping("/{emp_id}")
    public ResponseEntity<?> deleteemp(@PathVariable Long emp_id)
    {
        EmployeeDto ss=studentService.deleteEmployee(emp_id);
        log.info("Deleted employee with ID: {}", emp_id);
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
        log.info("Retrieving all employees");
        log.debug("All employee details: {}", employees);
        if (employees.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("No employees found");
        }
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/external")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesByName() {
        try {
            List<EmployeeDto> employees = restClient.get()
                    .uri("/testingbeeceptor")
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            (request, response) -> {
                                throw new RuntimeException(
                                        "Failed to fetch employees from the external service: "
                                                + response.getStatusCode()
                                );
                            }
                    ).body(new ParameterizedTypeReference<List<EmployeeDto>>() {});
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            log.error("Error occurred while fetching employees from the external service", e);
            throw new RuntimeException(
                    "Failed to fetch employees from the external service", e
            );
        }
    }

    @PostMapping("/external")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        EmployeeDto emp= restClient.post()
                .uri("/testingbeeceptorpost")
                .body(employeeDto)
                .retrieve()
                .body(EmployeeDto.class);
         System.out.println(emp);
        return ResponseEntity.ok(emp);
    }
}
