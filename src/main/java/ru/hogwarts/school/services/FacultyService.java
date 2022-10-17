package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentService studentService;

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        Faculty editFaculty = findFaculty(faculty.getId());
        editFaculty.setName(faculty.getName());
        editFaculty.setColor(faculty.getColor());
        return facultyRepository.save(faculty);

    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException());
    }

    public Collection<Faculty> getAllFaculties() {
        return List.copyOf(facultyRepository.findAll());
    }

    public List<Faculty> findFacultyByColor(String color) {
        return List.copyOf(facultyRepository.findByColorIgnoreCase(color));
    }

    public List<Faculty> findFacultyByColorOrName(String colorOrName) {
        return List.copyOf(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName));
    }

    public Faculty findFacultiesByStudents(Long id) {
        Student student = studentService.findStudent(id);
        if (student != null) {
            return student.getFaculty();
        }
        throw new StudentNotFoundException();
    }

    public Collection<Student> getStudentByFaculty(long id) {
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElseThrow(() -> new FacultyNotFoundException());
    }

}
