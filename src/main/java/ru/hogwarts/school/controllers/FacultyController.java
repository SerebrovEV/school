package ru.hogwarts.school.controllers;

import liquibase.pro.packaged.I;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.FacultyService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findFaculty(id));
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editeFaculty = facultyService.editFaculty(faculty);
        if (editeFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editeFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student")
    public ResponseEntity findFacultiesByStudent(@RequestParam Long idStudent) {
        return ResponseEntity.ok(facultyService.findFacultiesByStudents(idStudent));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsOnFaculty(@PathVariable Long id) {
       return ResponseEntity.ok(facultyService.getStudentByFaculty(id));
    }

    @GetMapping(params = "color")
    public ResponseEntity<List<Faculty>> findFacultiesByColor(@RequestParam(required = false) String color) {
            return ResponseEntity.ok(facultyService.findFacultyByColor(color));
    }


    @GetMapping(params = "colorOrName")
    public ResponseEntity<List<Faculty>> findFacultiesByColorOrNAme(@RequestParam(required = false) String colorOrName) {
            return ResponseEntity.ok(facultyService.findFacultyByColorOrName(colorOrName));
    }

    @GetMapping("/longest-name-of-faculty")
    public ResponseEntity<String> getLongestNameOfFaculty () {
        return ResponseEntity.ok(facultyService.getLongestNameOfFaculty());
    }

    @GetMapping("/task-4-5-4")
    public ResponseEntity<Integer> getTask4_5_4() {
        return ResponseEntity.ok(facultyService.task4_5_4());
    }

}
