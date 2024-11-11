package ru.hogwarts.school.services;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student addStudent (Student student);
    Student findStudent (long id);
    Student editStudent (Student student);
    void expelStudent (long id);
    Collection<Student> findByAge(int age);
    Faculty getById(long id);
    Collection <Student> findByAgeBetween(int min, int max);
    Integer getAllByCount ();
    Integer getAvgAgeStudents ();
    List<Student> getStudentGroupById ();
   List <Student> getStudentsByName(String name);
}
