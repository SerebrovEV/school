package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Faculty;

import java.util.Optional;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

}
