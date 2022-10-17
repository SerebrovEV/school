package ru.hogwarts.school.service;

import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.r2dbc.OptionsCapableConnectionFactory;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;


    @InjectMocks
    private FacultyService out;


    @Test
    public void positiveTest() {
        Faculty faculty1 = new Faculty();
        Faculty faculty2 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("test");
        faculty1.setColor("testColor");

        when(facultyRepository.findAll()).thenReturn(List.of());
        assertThat(out.getAllFaculties()).isEmpty();
        verify(facultyRepository,only()).findAll();

        when(facultyRepository.save(any())).thenReturn(faculty1);
        assertThat(out.createFaculty(faculty1)).isEqualTo(faculty1);
        verify(facultyRepository, times(1)).save(any());

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty1));
        assertThat(out.findFaculty(1L)).isEqualTo(faculty1);
        verify(facultyRepository, times(1)).findById(1L);

        when(facultyRepository.save(faculty1)).thenReturn(faculty2);
        assertThat(out.editFaculty(faculty1)).isEqualTo(faculty2);
        verify(facultyRepository, times(2)).save(faculty1);

        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(anyString(),anyString())).thenReturn(List.of(faculty1));
        assertThat(out.findFacultyByColorOrName("test")).isEqualTo(List.of(faculty1));
        verify(facultyRepository,times(1)).findByColorIgnoreCaseOrNameIgnoreCase(anyString(), anyString());

    }
}
