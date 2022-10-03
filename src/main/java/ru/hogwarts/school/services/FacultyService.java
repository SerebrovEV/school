package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> FACULTIES = new HashMap<>();

    private long facultyId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++facultyId);
        FACULTIES.put(facultyId, faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return FACULTIES.remove(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (FACULTIES.containsKey(faculty.getId())) {
            FACULTIES.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty findFaculty(long id) {
        return FACULTIES.get(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return List.copyOf(FACULTIES.values());
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        return getAllFaculties().stream()
                .filter(s -> s.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
