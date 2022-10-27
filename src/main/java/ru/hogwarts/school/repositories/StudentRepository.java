package ru.hogwarts.school.repositories;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findStudentsByAgeBetween(int min, int max);

    List<Student> findStudentsByFaculty_Id(Long id);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer countAllStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Double getMiddleAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5 ", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
