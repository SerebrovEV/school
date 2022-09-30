package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentServiceTest {

    private final StudentService out = new StudentService();

    @Test
    public void addTest() {
        assertThat(out.getAllStudents()).isEmpty();

        Student student1 = new Student(1L, "TestName1", 25);
        Student student2 = new Student(1L, "TestName2", 26);

        out.createStudent(student1);
        assertThat(out.findStudent(1)).isEqualTo(student1);

        out.editStudent(student2);
        assertThat(out.findStudent(1)).isEqualTo(student2);


        assertThat(out.findStudentsByAge(26)).isEqualTo(Stream.of(student2).toList());

        out.deleteStudent(1);
        assertThat(out.getAllStudents()).isEmpty();
    }
}
