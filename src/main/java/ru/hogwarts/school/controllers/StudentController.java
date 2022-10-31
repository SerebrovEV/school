package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    @GetMapping("/all")
    public ResponseEntity getAllStudents(@RequestParam(required = false) Long idFaculty,
                                         @RequestParam(required = false) String name) {
        if (idFaculty != null) {
            return ResponseEntity.ok(studentService.getAllStudentsOnFaculty(idFaculty));
        }
        if (name != null) {
            return ResponseEntity.ok(studentService.getAllStudentsWithLetter(name));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findFacultyByStudent(id));
    }


    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editStudent = studentService.editStudent(student);
        if (editStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "age")
    public ResponseEntity<Collection<Student>> findStudentSByAge(@RequestParam(required = false) int age) {

        return ResponseEntity.ok(studentService.findByAge(age));

    }

    @GetMapping(params = {"minAge", "maxAge"})
    public ResponseEntity<Collection<Student>> findStudentSByBetweenAge(@RequestParam(required = false) int minAge,
                                                                        @RequestParam(required = false) int maxAge) {
        if (minAge > 0 && maxAge >= minAge) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/number-of-students")
    public ResponseEntity<Integer> getNumberOfStudents() {
        return ResponseEntity.ok(studentService.getNumberOfStudents());
    }

    @GetMapping("/middle-age-students/{version}")
    public ResponseEntity getMiddleAgeOfStudents(@PathVariable int version) {
        if (version == 1) {
            return ResponseEntity.ok(studentService.getMiddleAgeOfStudents());
        }
        if (version == 2) {
            return ResponseEntity.ok(studentService.getMiddleAge());
        }
        return ResponseEntity.ok("Выберите режим работы: 1 или 2!");
    }
    @GetMapping("/five-last-student")
    public ResponseEntity<List<Student>> getLstStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

}
