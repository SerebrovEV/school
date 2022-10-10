package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
       return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getAllStudents() {
        return List.copyOf(studentRepository.findAll());
    }

    public Collection<Student> getAllStudentsOnFaculty(Long id) {
        return List.copyOf(studentRepository.findStudentsByFaculty_Id(id));
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

}
