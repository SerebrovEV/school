package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.models.Student;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void getAllStudents() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all/", String.class))
                .isNotEmpty();
    }

    @Test
    public void getAllStudentsOnFaculty() throws Exception {
        final int number1 = 1;
        final int number2 = 2;
        final int number3 = 3;
        final int number4 = 4;

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number1, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number2, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number3, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number4, String.class))
                .isNotNull();
    }

    @Test
    public void findStudentsByAge() throws Exception {
        Student student = new Student();
        student.setAge(22);
        student.setId(1L);
        student.setName("test");

        Student student2 = new Student();
        student2.setAge(23);
        student2.setId(2L);
        student2.setName("test");


        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isEqualTo(student);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class))
                .isEqualTo(student2);

        int number1 = 22;
        int number2 = 23;

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?minAge=" + number1 + "&maxAge=" + number2, String.class))
                .isNotNull();


    }

    @Test
    public void createAndGetStudent() throws JsonProcessingException {
        Student student = new Student();
        student.setAge(22);
        student.setId(1L);
        student.setName("test");

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class))
                .isEqualTo(student);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 1L, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 1L, String.class))
                .isEqualTo(objectMapper.writeValueAsString(student));
    }

    @Test
    public void getInfoAboutStudents() {

        Student student = new Student();
        student.setAge(22);
        student.setId(1L);
        student.setName("test");

        Student student2 = new Student();
        student2.setAge(28);
        student2.setId(2L);
        student2.setName("test2");

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class))
                .isEqualTo(student);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student2, Student.class))
                .isEqualTo(student2);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 1L, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 2L, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/number-of-students", Integer.class))
                .isEqualTo(2);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/middle-age-students", Integer.class))
                .isEqualTo((22 + 28) / 2);
    }

    @Test
    public void lastStudents() throws JsonProcessingException {
        Student student = new Student();
        student.setAge(22);
        student.setId(1L);
        student.setName("test");

        Student student2 = new Student();
        student2.setAge(28);
        student2.setId(2L);
        student2.setName("test2");

        Student student3 = new Student();
        student3.setAge(22);
        student3.setId(3L);
        student3.setName("test3");

        Student student4 = new Student();
        student4.setAge(28);
        student4.setId(4L);
        student4.setName("test4");


        Student student5 = new Student();
        student5.setAge(28);
        student5.setId(5L);
        student5.setName("test5");

        Student student6 = new Student();
        student6.setAge(28);
        student6.setId(6L);
        student6.setName("test6");

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class))
                .isEqualTo(student);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student2, Student.class))
                .isEqualTo(student2);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student3, Student.class))
                .isEqualTo(student3);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student4, Student.class))
                .isEqualTo(student4);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student5, Student.class))
                .isEqualTo(student5);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student6, Student.class))
                .isEqualTo(student6);


        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student//five-last-student", List.class))
                .isNotNull();
    }

}
