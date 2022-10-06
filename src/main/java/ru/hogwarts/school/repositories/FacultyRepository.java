package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Faculty;



public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

}
