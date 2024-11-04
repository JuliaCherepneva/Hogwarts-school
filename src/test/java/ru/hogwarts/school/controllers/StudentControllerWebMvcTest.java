package ru.hogwarts.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultActionsDsl;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.StudentService;
import ru.hogwarts.school.services.StudentServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest( controllers = StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;



    @Test
    void shouldGetStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student(12, "Вася");

        when(studentService.findStudent(studentId)).thenReturn(student);
        // when
        ResultActions perform = mockMvc.perform(get("/student/{id}", studentId));
        // then
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

    }

    @Test
    void shouldCreateStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student(20, "Ivan");
        Student savedStudent = new Student(20, "Ivan");
        savedStudent.setId(studentId);
        when(studentService.addStudent(student)).thenReturn(savedStudent);
        // when
        ResultActions perform = mockMvc.perform(post("/student/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        // then
        perform
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()))
                .andExpect(jsonPath("$.age").value(savedStudent.getAge()))
                .andDo(print());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        // given
        Student student = new Student(20, "Ivan");
        when(studentService.editStudent(student)).thenReturn(student);
        // when
        ResultActions perform = mockMvc.perform(put("/student/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        // then
        perform
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        // given
        Long studentId = 1L;
        Student student = new Student(20, "Ivan");
        // when
        ResultActions perform = mockMvc.perform(delete("/student/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
        // then
        perform
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetStudentByAge() throws Exception {
        // given
        Collection<Student> students = new ArrayList<>();
        Student student1 = new Student(12, "Вася");
        Student student2 = new Student(15, "Вася");

        when(studentService.findByAgeBetween(11, 16)).thenReturn(new ArrayList<>());
        // when
        ResultActions perform = mockMvc.perform(get("/student/filter?min=11&max=16"));
        // then
        perform
                .andExpect(content().json(objectMapper.writeValueAsString(students)));

    }

}


