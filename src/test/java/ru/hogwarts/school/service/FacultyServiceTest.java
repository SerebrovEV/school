package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.services.FacultyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

        when(facultyRepository.findAll()).thenReturn(List.of());
        assertThat(out.getAllFaculties()).isEmpty();

        when(facultyRepository.save(any())).thenReturn(faculty1);
        assertThat(out.createFaculty(faculty1)).isEqualTo(faculty1);

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty1));
        assertThat(out.findFaculty(1L)).isEqualTo(faculty1);

        when(facultyRepository.save(faculty1)).thenReturn(faculty2);
        assertThat(out.editFaculty(faculty1)).isEqualTo(faculty2);

        when(facultyRepository.findByColor(any())).thenReturn(List.of(faculty1, faculty2));
        assertThat(out.findFacultyByColor("test")).isEqualTo(List.of(faculty1, faculty2));
    }
}
