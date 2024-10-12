package ru.hogwarts.school.services;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;



@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student addStudent (Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent (long id) {
        return studentRepository.findById(id);
    }

    public Student editStudent (Student student) {
        return studentRepository.save(student);
    }

    public void expelStudent (long id) {
        studentRepository.deleteById(id);
    }
    public Collection <Student> findByAge(int age) {
        return studentRepository.findByAge(age);

    }


    public Collection <Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getById(long id) {
        Student student = studentRepository.findById(id);
        return student.getFaculty();

    }
}
