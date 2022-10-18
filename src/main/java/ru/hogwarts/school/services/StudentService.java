package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Student editStudent(Student student) {
        Student editStudent = findStudent(student.getId());
        editStudent.setName(student.getName());
        editStudent.setAge(student.getAge());
        return studentRepository.save(student);

    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException());
    }

    public Collection<Student> getAllStudents() {
        return List.copyOf(studentRepository.findAll());
    }

    public Collection<Student> getAllStudentsOnFaculty(Long id) {
        return List.copyOf(studentRepository.findStudentsByFaculty_Id(id));
    }

    public List<Student> findByAge(int age) {
        return List.copyOf(studentRepository.findByAge(age));
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Faculty findFacultyByStudent(Long id) {
        return findStudent(id).getFaculty();
    }

    public Integer getNumberOfStudents() {
        return studentRepository.countAllStudents();
    }

    public Double getMiddleAgeOfStudent() {
        Double result = studentRepository.getMiddleAgeOfStudent();
        Double scale = Math.pow(10, 2);
        return Math.ceil(result*scale)/scale;
    }

    public List<Student> getLastFiveStudents() {
        return List.copyOf(studentRepository.getLastFiveStudents());
    }

}
