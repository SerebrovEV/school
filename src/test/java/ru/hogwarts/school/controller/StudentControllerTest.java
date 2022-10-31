package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;


import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StudentController studentController;

    private final Faker faker = new Faker();

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void getAllStudents() throws JsonProcessingException {
        Student student = generateStudent();
        student.setId(1L);
        Student student2 = generateStudent();
        student2.setId(2L);
        Student student3 = generateStudent();
        student3.setId(3L);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isEqualTo(student);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class))
                .isEqualTo(student2);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student3, Student.class))
                .isEqualTo(student3);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all/", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all/", String.class))
                .isEqualTo(objectMapper.writeValueAsString(List.of(student, student2, student3)));
    }

    @Test
    public void getAllStudentsOnFaculty() throws Exception {
        final long number1 = 1;
        final int number2 = 2;
        final int number3 = 3;
        final int number4 = 4;
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty" + number1, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number1, String.class))
                .isEqualTo(objectMapper.writeValueAsString(List.of()));
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number2, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number2, String.class))
                .isEqualTo(objectMapper.writeValueAsString(List.of()));
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number3, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number3, String.class))
                .isEqualTo(objectMapper.writeValueAsString(List.of()));
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number4, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/all?idFaculty=" + number4, String.class))
                .isEqualTo(objectMapper.writeValueAsString(List.of()));
}

    @Test
    public void findStudentsByAgeBetween() throws Exception {
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
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?minAge=" + number1 + "&maxAge=" + number2, String.class))
                .isEqualTo(objectMapper.writeValueAsString(List.of(student, student2)));


    }

    @Test
    public void findStudentsByAge() throws Exception {
        Student student = generateStudent();
        student.setId(1L);

        int number1 = student.getAge();

        Student student2 = generateStudent();
        student2.setId(2L);
        student2.setAge(number1);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isEqualTo(student);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class))
                .isEqualTo(student2);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?age=" + number1, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?age=" + number1, String.class))
                .isEqualTo(objectMapper.writeValueAsString(List.of(student, student2)));


    }


    @Test
    public void createAndGetStudent() throws JsonProcessingException {
        Student student = generateStudent();
        student.setId(1L);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class))
                .isEqualTo(student);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 1L, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + 1L, String.class))
                .isEqualTo(objectMapper.writeValueAsString(student));
    }

    @Test
    public void getInfoAboutStudents() {

        Student student = generateStudent();
        student.setId(1L);

        Student student2 = generateStudent();
        student2.setId(2L);

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
                .isEqualTo((student.getAge() + student2.getAge()) / 2);
    }

    @Test
    public void lastStudents() throws JsonProcessingException {
        Student student = generateStudent();
        student.setId(1L);

        Student student2 = generateStudent();
        student2.setId(2L);

        Student student3 = generateStudent();
        student3.setId(3L);

        Student student4 = generateStudent();
        student4.setId(4L);

        Student student5 = generateStudent();
        student5.setId(5L);

        Student student6 = generateStudent();
        student6.setId(6L);

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
        assertThat(objectMapper.writeValueAsString(this.restTemplate.getForObject("http://localhost:" + port + "/student//five-last-student", List.class)))
                .isEqualTo(objectMapper.writeValueAsString(List.of(student6, student5, student4, student3, student2)));
    }

    private Student generateStudent() {
        Student student = new Student();
        student.setName(faker.harryPotter().character());
        student.setAge(faker.random().nextInt(11, 18));
        return student;

    }

    private Student generateStudent(Faculty faculty) {
        Student student = new Student();
        student.setName(faker.harryPotter().character());
        student.setAge(faker.random().nextInt(11, 18));
        student.setFaculty(faculty);
        return student;

    }

    private Faculty generateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());
        return faculty;
    }

    @Test
    public void findByAgeBetweenTest() throws JsonProcessingException {
        List<Faculty> facultyList = Stream.generate(this::generateFaculty)
                .limit(5)
                .map(faculty -> restTemplate.postForObject("http://localhost:" + port + "/faculty/", faculty, Faculty.class))
                .toList();
        List<Student> studentList = Stream.generate(() -> generateStudent(facultyList.get(faker.random().nextInt(facultyList.size()))))
                .limit(50)
                .map(student -> restTemplate.postForObject("http://localhost:" + port + "/student/", student, Student.class))
                .toList();
        int minAge = 14;
        int maxAge = 17;
        List<Student> expectedStudent = studentList.stream()
                .filter(student -> student.getAge() >= minAge && student.getAge() <= maxAge)
                .toList();

        ResponseEntity<List<Student>> getForResponse = restTemplate.exchange(
                "http://localhost:" + port + "/student?minAge={minAge}&maxAge={maxAge}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                },
                minAge,
                maxAge
        );
        assertThat(getForResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?minAge=" + minAge + "&maxAge=" + maxAge, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student?minAge=" + minAge + "&maxAge=" + maxAge, String.class))
                .isEqualTo(objectMapper.writeValueAsString(expectedStudent));
        assertThat(getForResponse.getBody())
                .hasSize(expectedStudent.size())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedStudent);
    }

}
