package ru.hogwarts.school.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentService;
import ru.hogwarts.school.services.StudentServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping ("/add")
    public Student addStudent(@RequestBody Student student) {

        return studentService.addStudent(student);
    }

    @PutMapping ("/edit")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editStudent = studentService.editStudent(student);
        if (editStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> expelStudent(@PathVariable Long id) {
        studentService.expelStudent(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping ("/filter")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) int min,
                                                            @RequestParam(required = false) int max) {

        if (min  > 0 && max > 0) {
            return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/studentsFaculty/{id}")
    public Faculty getFaculty (@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping ("/studentCount")
    public Integer getAllByCount () {
        return studentService.getAllByCount();
    }

    @GetMapping ("/avgAge")
    public Integer getAvgAgeStudents () {
        return studentService.getAvgAgeStudents();
    }

    @GetMapping ("/GroupById")
    public List <Student> getStudentGroupById () {
        return studentService.getStudentGroupById();
    }


    @GetMapping ("/name/{name}")
    public ResponseEntity<List <Student>> getStudentsByName (@PathVariable String name) {
        List <Student> students = studentService.getStudentsByName(name);
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }


}
