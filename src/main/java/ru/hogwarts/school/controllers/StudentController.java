package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createFaculty(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getFacultyInfo(@PathVariable Long id) {
        Student findStudent = studentService.findStudent(id);
        if (findStudent == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(findStudent);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllFaculties() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping
    public ResponseEntity<Student> editFaculty(@RequestBody Student student) {
        Student editStudent = studentService.editStudent(student);
        if (editStudent == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteFaculty(@PathVariable Long id) {
        Student deleteStudent = studentService.deleteStudent(id);
        if (deleteStudent == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(deleteStudent);
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> findStudentSByAge(@RequestParam("age") int age) {
        Collection<Student> findStudents = studentService.findStudentsByAge(age);
        if (findStudents == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(findStudents);
    }
    }
