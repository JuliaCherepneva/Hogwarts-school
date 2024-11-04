package ru.hogwarts.school.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Student findById(long id);

    @Query (value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getAllByCount ();

    @Query (value = "SELECT AVG(age) from student", nativeQuery = true)
    Integer getAvgAgeStudents ();


    @Query (value = "SELECT * FROM student order by id desc limit 5", nativeQuery = true)
    List <Student> getStudentGroupById();



}
