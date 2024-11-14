package ru.hogwarts.school.services;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


    public Student addStudent (Student student) {
        logger.debug("Requesting added a student: {}", student);
        Student save = studentRepository.save(student);
        logger.debug("The student {} added", student);
        return save;
    }

    public Student findStudent (long id) {
        logger.debug("Requesting to find student by id: {}", id);
        final Student byId = studentRepository.findById(id);
        logger.debug("The student by id {} finded", id);
        return byId;
    }

    public Student editStudent (Student student) {
        logger.debug("Requesting edit a student: {}", student);
        final Student saveEdit = studentRepository.save(student);
        logger.debug("The student {} edited", student);
        return saveEdit;
    }

    public void expelStudent (long id) {
        logger.debug("Requesting to delete student by id: {}", id);
        studentRepository.deleteById(id);
        logger.debug("The student by id {}  deleted", id);
    }

    public Collection <Student> findByAge(int age) {
        logger.debug("Requesting to find students by age: {}", age);
        final List<Student> byAge = studentRepository.findByAge(age);
        logger.debug("The students by age {}  finded", age);
        return byAge;
    }


    public Collection <Student> findByAgeBetween(int min, int max) {
        logger.debug("Requesting to find students by age between min {} and max: {}", min, max);
        final Collection<Student> byAgeBetween = studentRepository.findByAgeBetween(min, max);
        logger.info("The students finded");
        return byAgeBetween;
    }

    public Faculty getById(long id) {
        logger.debug("Requesting to find faculty by student's id: {}", id);
        Student student = studentRepository.findById(id);
        logger.debug("The faculty by student's id {}  finded", id);
        return student.getFaculty();
    }
    public Integer getAllByCount () {
        final Integer allByCount = studentRepository.getAllByCount();
        logger.info("All students found");
        return allByCount;
    }

    public  Integer getAvgAgeStudents () {
        final Integer avgAgeStudents = studentRepository.getAvgAgeStudents();
        logger.info("The average age of students was obtained");
        return avgAgeStudents;

    }

    public List <Student> getStudentGroupById () {
        final List<Student> studentGroupById = studentRepository.getStudentGroupById();
        logger.info("Grouping students by id is complete");
        return studentGroupById;
    }

    public List <Student> getStudentsByName (String name) {
        final List<Student> studentsByName = studentRepository.getStudentsByName(name);
        logger.info("The students found");
        return studentsByName;
    }

    public List<String> filterByName () {
        return studentRepository.findAll()
                .stream()
                .parallel()
                .filter(i -> i.getName().startsWith ("А"))
                .map (i->i.getName().toUpperCase())
                .sorted()
                .toList();
    }

    public Double filterByAvg () {
        return studentRepository.findAll()
                .stream()
                .parallel()
                .mapToDouble (Student :: getAge)
                .average().orElseThrow();
    }
}
