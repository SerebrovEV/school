package ru.hogwarts.school.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentService studentService;

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);


    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        Faculty findFaculty = findFaculty(id);
        logger.warn("Was invoked method for delete faculty = {}", findFaculty);
        facultyRepository.deleteById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        Faculty editFaculty = findFaculty(faculty.getId());
        logger.warn("Was invoked method for edit faculty = {}", faculty);
        editFaculty.setName(faculty.getName());
        editFaculty.setColor(faculty.getColor());
        return facultyRepository.save(faculty);

    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find faculty");
        Optional<Faculty> findFaculty = facultyRepository.findById(id);
        if (findFaculty.isPresent()) {
            logger.debug("Method find faculty");
            return findFaculty.get();
        } else {
            logger.error("There is not faculty with id = {}", id);
            throw new FacultyNotFoundException();
        }
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculty");
        return List.copyOf(facultyRepository.findAll());
    }

    public List<Faculty> findFacultyByColor(String color) {
        logger.info("Was invoked method for find faculty by color");
        return List.copyOf(facultyRepository.findByColorIgnoreCase(color));
    }

    public List<Faculty> findFacultyByColorOrName(String colorOrName) {
        logger.info("Was invoked method for find faculty by color or name");
        return List.copyOf(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName));
    }

    public Faculty findFacultiesByStudents(Long id) {
        logger.info("Was invoked method for find faculty by student");
        Student student = studentService.findStudent(id);
        if (student != null) {
            logger.debug("Method found faculty");
            return student.getFaculty();
        }
        logger.error("There is not faculty with student id = {}", id);
        throw new StudentNotFoundException();
    }

    public Collection<Student> getStudentByFaculty(long id) {
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElseThrow(FacultyNotFoundException::new);
    }

    public String getLongestNameOfFaculty() {
        return facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparingInt(String::length))
                .get();

    }

    public Integer task4_5_4() {
        Long time = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        time = System.currentTimeMillis() - time;
        logger.debug("time = {}", time);
        return sum;
    }

}
