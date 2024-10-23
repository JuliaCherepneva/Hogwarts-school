package ru.hogwarts.school.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    Faculty faculty;

    @BeforeEach
    public void clearDB(){
        facultyRepository.deleteAll();
        faculty = new Faculty("Гриффиндор", "Красный и жёлтый");
        faculty = facultyRepository.save(faculty);
    }


    @Test
    void shouldCreateFaculty() {
        //when
        ResponseEntity <Faculty> facultyResponseEntity = restTemplate.postForEntity (
                "http://localhost:" + port + "/faculty/add", faculty, Faculty.class);
        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Faculty actual = facultyResponseEntity.getBody();
        assertNotNull(actual.getId());
        assertEquals(faculty.getName(),actual.getName());
        assertEquals(faculty.getColor(),actual.getColor());

    }

    @Test
    void shouldGetFaculty() {


        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                Faculty.class
        );

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(),
                HttpStatusCode.valueOf(200));
        Faculty actual = facultyResponseEntity.getBody();
        assertEquals(faculty.getName(), actual.getName());
        assertEquals(faculty.getId(), actual.getId());
        assertEquals(faculty.getColor(), actual.getColor());
    }

    @Test
    void shouldEditFaculty() {

        //when
        ResponseEntity <Faculty> facultyResponseEntity = restTemplate.exchange(String.format(
                        "http://localhost:" + port + "/faculty/edit"),
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Faculty.class);
        //then
        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Faculty actual = facultyResponseEntity.getBody();
        assertNotNull(actual.getId());
        assertEquals(faculty.getName(),actual.getName());
        assertEquals(faculty.getColor(),actual.getColor());
    }

    @Test
    void shouldDeleteFaculty() {
        //when
        ResponseEntity <Faculty> facultyResponseEntity = restTemplate.exchange(String.format(
                "http://localhost:" + port + "/faculty/" + faculty.getId()),
                HttpMethod.DELETE,  new HttpEntity<>(" "), Faculty.class);


        //then
        assertNull(facultyResponseEntity.getBody());
        assertEquals(facultyResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        final Optional<Faculty> byId = facultyRepository.findById(faculty.getId());
        assertEquals(false,byId.isPresent());

    }

    @Test
    void shouldGetFacultyByColor() {
        ResponseEntity <Collection<Faculty>> facultyResponseEntity = restTemplate.exchange("http://localhost:" + port + "/faculty/filter?color=" + faculty.getColor(),
        HttpMethod.GET,
                null,
                new ParameterizedTypeReference <Collection <Faculty>> () {});

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(),
                HttpStatusCode.valueOf(200));
        Collection <Faculty> actual = facultyResponseEntity.getBody();
        assertEquals(actual.isEmpty(), false);
        final ArrayList<Faculty> faculties = new ArrayList<>(actual);
        assertEquals(faculty.getName(), faculties.get(0).getName());
        assertEquals(faculty.getId(), faculties.get(0).getId());
        assertEquals(faculty.getColor(), faculties.get(0).getColor());
    }

    @Test
    void shouldGetFacultyByName() {
        ResponseEntity <Collection<Faculty>> facultyResponseEntity = restTemplate.exchange("http://localhost:" + port + "/faculty/filter?name=" + faculty.getName(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference <Collection <Faculty>> () {});

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(),
                HttpStatusCode.valueOf(200));
        Collection <Faculty> actual = facultyResponseEntity.getBody();
        assertEquals(actual.isEmpty(), false);
        final ArrayList<Faculty> faculties = new ArrayList<>(actual);
        assertEquals(faculty.getName(), faculties.get(0).getName());
        assertEquals(faculty.getId(), faculties.get(0).getId());
        assertEquals(faculty.getColor(), faculties.get(0).getColor());
    }
    @Test
    void shouldGetFacultyByFilter() {
        ResponseEntity <Collection<Faculty>> facultyResponseEntity = restTemplate.exchange("http://localhost:" + port + "/faculty/filter",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference <Collection <Faculty>> () {});

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(),
                HttpStatusCode.valueOf(200));
        Collection <Faculty> actual = facultyResponseEntity.getBody();
        assertEquals(actual.isEmpty(), true);

    }

    @Test
    void shouldGetStudents() {
        ResponseEntity <Set<Student>> facultyResponseEntity = restTemplate.exchange("http://localhost:" + port + "/faculty/students/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference <Set<Student>>  () {});

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(),
                HttpStatusCode.valueOf(200));
        Set<Student>  actual = facultyResponseEntity.getBody();
        assertEquals(actual.isEmpty(), true);

    }

    @Test
    void shouldGetStudent() {

        //given
        final Faculty faculty1 = facultyRepository.findById(faculty.getId()).orElse(null);
        assertNotNull(faculty1);
        final Student studentEntity = new Student(18, "Макс");
        studentEntity.setFaculty(faculty1);
        final Student student1 = studentRepository.save(studentEntity);

        ResponseEntity <Set<Student>> facultyResponseEntity = restTemplate.exchange("http://localhost:" + port + "/faculty/students/" + student1.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference <Set<Student>>  () {});

        assertNotNull(facultyResponseEntity);
        assertEquals(facultyResponseEntity.getStatusCode(),
                HttpStatusCode.valueOf(200));
        Set<Student>  actual = facultyResponseEntity.getBody();
        assertEquals(actual.isEmpty(), false);
        assertEquals(1,actual.size());
        final ArrayList<Student> students = new ArrayList<>(actual);
        assertEquals(student1.getName(), students.get(0).getName());
        assertEquals(student1.getId(), students.get(0).getId());

    }

}




