package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final HashMap<Long, Student> STUDENTS = new HashMap<>();
    private long studentId = 0;

    public Student createStudent(Student student) {
        student.setId(++studentId);
        STUDENTS.put(studentId, student);
        return student;
    }

    public Student deleteStudent(long id) {
        return STUDENTS.remove(id);
    }

    public Student editStudent(Student student) {
        if (STUDENTS.containsKey(student.getId())) {
            STUDENTS.put(student.getId(), student);
        return student;
        }
        return null;
    }

    public Student findStudent(long id) {
        return STUDENTS.get(id);
    }

    public Collection<Student> getAllStudents() {
        return List.copyOf(STUDENTS.values());
    }

    public Collection<Student> findStudentsByAge(int age) {
        return getAllStudents().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
