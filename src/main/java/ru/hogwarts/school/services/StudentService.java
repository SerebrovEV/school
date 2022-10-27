package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        Student deleteStudent = findStudent(id);
        logger.warn("Was invoked method for delete student = {}", deleteStudent);
        studentRepository.deleteById(id);
    }

    public Student editStudent(Student student) {
        Student editStudent = findStudent(student.getId());
        logger.warn("Was invoked method for edit student = {}", student);
        editStudent.setName(student.getName());
        editStudent.setAge(student.getAge());
        return studentRepository.save(student);

    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student");
        Optional<Student> findStudent = studentRepository.findById(id);
        if (findStudent.isPresent()) {
            logger.debug("Method find student");
            return findStudent.get();
        } else {
            logger.error("There is not student with id = {}", id);
            throw new StudentNotFoundException();
        }
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        return List.copyOf(studentRepository.findAll());
    }

    public Collection<Student> getAllStudentsOnFaculty(Long id) {
        logger.info("Was invoked method for get all students on faculty");
        return List.copyOf(studentRepository.findStudentsByFaculty_Id(id));
    }

    public List<Student> findByAge(int age) {
        logger.info("Was invoked method for find students by age");
        return List.copyOf(studentRepository.findByAge(age));
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between values");
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Faculty findFacultyByStudent(Long id) {
        logger.info("Was invoked method for find faculty by student");
        return findStudent(id).getFaculty();
    }

    public Integer getNumberOfStudents() {
        logger.info("Was invoked method for count all students");
        return studentRepository.countAllStudents();
    }

    public Double getMiddleAgeOfStudents() {
        logger.info("Was invoked method for get middle age of students");
        Double result = studentRepository.getMiddleAgeOfStudents();
        Double scale = Math.pow(10, 2);
        return Math.ceil(result*scale)/scale;
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return List.copyOf(studentRepository.getLastFiveStudents());
    }

}
