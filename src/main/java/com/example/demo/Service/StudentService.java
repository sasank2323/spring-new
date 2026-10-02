package com.example.demo.Service;

import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
@Service
public class StudentService {
    private StudentRepository studentRepository;

    StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<EmployeeDto> getStudentName(Long emp_id) {
      Optional<Student> s = studentRepository.findById(1L);
      if(s==null)
      {
          return Optional.empty();
      }
      EmployeeDto  e= new ModelMapper().map(s, EmployeeDto.class);
        return Optional.of(e);
    }

    public EmployeeDto storeEmployee(EmployeeDto employeeDto) {
        Student student = new ModelMapper().map(employeeDto, Student.class);
        student = studentRepository.save(student);
        return new ModelMapper().map(student, EmployeeDto.class);
    }

    public EmployeeDto deleteEmployee(Long emp_id) {
        Optional<Student> studentOptional = studentRepository.findById(emp_id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            studentRepository.delete(student);
            return new ModelMapper().map(student, EmployeeDto.class);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    public EmployeeDto updateEmployee(Long emp_id, EmployeeDto employeeDto) {
        Optional<Student> studentOptional = studentRepository.findById(emp_id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            // Update the fields of the existing student with the new values
            student.setName(employeeDto.getName());
            student.setEmail(employeeDto.getEmail());
            student.setAge(employeeDto.getAge());
            student.setDepartment(employeeDto.getDepartment());
            // Save the updated student back to the repository
            student = studentRepository.save(student);
            return new ModelMapper().map(student, EmployeeDto.class);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

   public List<EmployeeDto> getAllEmployees() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(student -> new ModelMapper().map(student, EmployeeDto.class))
                .collect(Collectors.toList());
    }
}
