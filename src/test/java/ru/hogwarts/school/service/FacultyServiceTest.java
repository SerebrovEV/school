package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.services.FacultyService;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class FacultyServiceTest {
    private final FacultyService out = new FacultyService();

    @Test
    public void addTest() {
        assertThat(out.getAllFaculties()).isEmpty();

        Faculty faculty1 = new Faculty(1L, "TestName1", "TestColor1");
        Faculty faculty2 = new Faculty(1L, "TestName2", "TestColor2");

        out.createFaculty(faculty1);
        assertThat(out.findFaculty(1)).isEqualTo(faculty1);

        out.editFaculty(faculty2);
        assertThat(out.findFaculty(1)).isEqualTo(faculty2);

        assertThat(out.findFacultyByColor("TestColor2")).isEqualTo(Stream.of(faculty2).toList());

        out.deleteFaculty(1);
        assertThat(out.getAllFaculties()).isEmpty();
    }
}
