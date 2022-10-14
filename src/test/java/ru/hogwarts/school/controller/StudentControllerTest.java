package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.models.Student;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest  {

    @LocalServerPort
    private int port;

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
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/all/",String.class))
                .isNotEmpty();
    }

    @Test
    public void getAllStudentsOnFaculty() throws Exception {
        final int number1 = 1;
        final int number2 = 2;
        final int number3 = 3;
        final int number4 = 4;

        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/all?idFaculty="+number1,String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/all?idFaculty="+number2,String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/all?idFaculty="+number3,String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/all?idFaculty="+number4,String.class))
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


        assertThat(this.restTemplate.postForObject("http://localhost:"+port+"/student",student,Student.class))
                .isEqualTo(student);
      assertThat(this.restTemplate.postForObject("http://localhost:"+port+"/student",student2,Student.class))
               .isEqualTo(student2);

        int number1 = 22;
        int number2 = 23;

        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student?minAge="+number1+"&maxAge="+number2,String.class))
                .isNotNull();


    }

    @Test
    public void createAndGetStudent() {
        Student student = new Student();
        student.setAge(22);
        student.setId(1L);
        student.setName("test");

        assertThat(this.restTemplate.postForObject("http://localhost:"+port+"/student/",student,Student.class))
                .isEqualTo(student);
       assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/"+1L,String.class))
              .isNotNull();
    }
}
