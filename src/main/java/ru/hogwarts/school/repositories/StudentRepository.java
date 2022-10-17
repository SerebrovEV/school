package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Student;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);
    List<Student> findStudentsByAgeBetween(int min, int max);

    List<Student> findStudentsByFaculty_Id(Long id);

}
