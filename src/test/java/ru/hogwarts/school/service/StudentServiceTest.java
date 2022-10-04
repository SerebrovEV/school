package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.StudentService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    private StudentService out;

    @Test
    public void positiveTest() {
        Student student1 = new Student();
        Student student2 = new Student();

        when(studentRepository.findAll()).thenReturn(List.of());
        assertThat(out.getAllStudents()).isEmpty();
        verify(studentRepository,only()).findAll();

        when(studentRepository.save(any())).thenReturn(student1);
        assertThat(out.createStudent(student1)).isEqualTo(student1);
        verify(studentRepository, times(1)).save(any());

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        assertThat(out.findStudent(1L)).isEqualTo(student1);
        verify(studentRepository, times(1)).findById(1L);

        when(studentRepository.save(student1)).thenReturn(student2);
        assertThat(out.editStudent(student1)).isEqualTo(student2);
        verify(studentRepository, times(2)).save(student1);

        when(studentRepository.findByAge(anyInt())).thenReturn(List.of(student1, student2));
        assertThat(out.findStudentsByAge(22)).isEqualTo(List.of(student1, student2));
        verify(studentRepository,times(1)).findByAge(anyInt());
    }
}
