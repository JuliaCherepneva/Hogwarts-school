package ru.hogwarts.school.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

Faculty faculty;
    @BeforeEach
    public void clearDB(){
        facultyRepository.deleteAll();
        Faculty faculty = new Faculty("Гриффиндор", "Красный");
    }


    @Test
    void shouldCreateFaculty() {
        //given

        //when
        ResponseEntity <Faculty> facultyResponseEntity = restTemplate.postForEntity (
                "http://localhost:" + port + "/faculties", faculty, Faculty.class);
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
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculties/" + faculty.getId(),
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
    }




