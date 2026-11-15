package com.example.demo.Service;

import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.TestContainerConfiguration;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@Import(TestContainerConfiguration.class)
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
// spy means using real ones because it was a third party library and we want to use the real one because it is a simple class and we can use it in our test cases
    @Spy
    private ModelMapper modelMapper;
    // if we have done mocks then all mocks will get injected into the studentService class and we can test the methods of studentService class
    @InjectMocks
    private StudentService studentService;

    @Test
    void getStudentName() {
        Long studentId = 1L;
        Student s= Student.builder().id(studentId).name("John Doe").email("saisasank2323@gamil.com")
                .age(25).department("Computer Science").build();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(s));
       Optional<EmployeeDto> d= studentService.getStudentName(studentId);
        assertThat(d.get().getId(), equalTo(studentId));
        assertThat(d.get().getName(), equalTo(s.getName()));
    }

    @Test
    void storeEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void getAllEmployees() {
    }
}