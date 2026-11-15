package com.example.demo.Service;

import com.example.demo.Dto.EmployeeDto;
import com.example.demo.Entity.Student;
import com.example.demo.Exception.ResourceNotFound;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.TestContainerConfiguration;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    private Student student;
    @BeforeEach
    public void setup() {
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("saisasank2323@gmail.com");
        student.setAge(25);
        student.setDepartment("Computer Science");
    }
    @Test
    void getStudentName() {
        Long studentId = 1L;
        Student s= Student.builder().id(studentId).name("John Doe").email("saisasank2323@gamil.com")
                .age(25).department("Computer Science").build();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(s));
       Optional<EmployeeDto> d= studentService.getStudentName(studentId);
        assertThat(d.get().getId(), equalTo(studentId));
        assertThat(d.get().getName(), equalTo(s.getName()));

        // what methods are get called in repo checking list
        verify(studentRepository).findById(studentId);// it will get successed because we are calling it
        //verify(studentRepository).save(s);// it will get failed because we are not calling it


    }

    @Test
    void storeEmployee() {

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        EmployeeDto storedEmployee = studentService.storeEmployee(new EmployeeDto(student.getId(), student.getName(), student.getEmail(), student.getAge(), student.getDepartment()));

        // ArgumentCaptor is used to capture the argument passed to the save method of studentRepository. It allows us to verify that the correct Student object was passed to the save method and also to assert its properties.
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        EmployeeDto capturedEmployeeDto = modelMapper.map(studentArgumentCaptor.getValue(), EmployeeDto.class);
        assertEquals(capturedEmployeeDto.getId(), equalTo(student.getId()));


        assertNotNull(storedEmployee);
        assertEquals(student.getId(), storedEmployee.getId());
        assertEquals(student.getName(), storedEmployee.getName());

        //verify part
        verify(studentRepository).save(any(Student.class));
        verify(studentRepository,times(1)).save(any(Student.class));// it will get successed because we are calling it only once
        verify(studentRepository,never()).findById(anyLong());// it will get successed because  we are not calling it
        verify(studentRepository,atLeastOnce()).save(any(Student.class));// it will get successed because we are calling it only once
        verify(studentRepository,atMostOnce()).save(any(Student.class));// it will get successed  because we are calling it only once
        verify(studentRepository,only()).save(any(Student.class));// it will get successed because we are calling it only once

    }

    @Test
    void deleteEmployee() {
        // if it was string you can pass anyString() and if it was long you can pass anyLong() and if it was int you can pass anyInt() and if it was double you can pass anyDouble() and if it was float you can pass anyFloat() and if it was boolean you can pass anyBoolean() and if it was char you can pass anyChar() and if it was byte you can pass anyByte() and if it was short you can pass anyShort() and if it was object you can pass any() and if it was list you can pass anyList() and if it was map you can pass anyMap() and if it was set you can pass anySet() and if it was array you can pass anyArray() and if it was collection you can pass anyCollection() and if it was iterable you can pass anyIterable() and if it was stream you can pass anyStream() and if it was optional you can pass anyOptional()
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.of(student));
        EmployeeDto deletedEmployee = studentService.deleteEmployee(student.getId());
        assertNotNull(deletedEmployee);
        assertEquals(student.getId(), deletedEmployee.getId());
        assertEquals(student.getName(), deletedEmployee.getName());

        //verify part
        verify(studentRepository).findById(student.getId());
        verify(studentRepository).delete(student);
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void getAllEmployees() {
    }

    @Test
    void testGetStudentName_ThrowsResourceNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFound.class, () -> {
            studentService.getStudentName(studentId);
        });
        String expectedMessage = "Employee not found testing";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDeleteEmployee_ThrowsRuntimeException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> studentService.deleteEmployee(studentId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee not found");
    }

    @Test
    void testUpdateEmployee_ThrowsRuntimeException() {
        Long studentId = 1L;
        EmployeeDto employeeDto = new EmployeeDto(studentId, "John Doe", "john.doe@example.com", 30, "IT");
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> studentService.updateEmployee(studentId, employeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee not found");
    }


}