package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        Student deleteStudent = findStudent(id);
        logger.warn("Was invoked method for delete student = {}", deleteStudent);
        studentRepository.deleteById(id);
    }

    public Student editStudent(Student student) {
        Student editStudent = findStudent(student.getId());
        logger.warn("Was invoked method for edit student = {}", student);
        editStudent.setName(student.getName());
        editStudent.setAge(student.getAge());
        return studentRepository.save(student);

    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student");
        Optional<Student> findStudent = studentRepository.findById(id);
        if (findStudent.isPresent()) {
            logger.debug("Method find student");
            return findStudent.get();
        } else {
            logger.error("There is not student with id = {}", id);
            throw new StudentNotFoundException();
        }
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        return List.copyOf(studentRepository.findAll());
    }

    public List<String> getAllStudentsWithLetter(String name) {
        logger.info("Was invoked method for get all students with name = {}", name);
        String letter = String.valueOf(name.charAt(0)).toUpperCase();

        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith(letter))
                .sorted()
                .collect(Collectors.toList());
    }

    public Double getMiddleAge() {
        logger.info("Was invoked method for get meddle age");

        Double result = studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);

//           Long numberOfStudent = studentRepository.findAll().stream()
//                     .count();
//        Integer ageStudent = studentRepository.findAll().stream()
//                .map(student -> student.getAge())
//                .reduce((x, y) -> x + y)
//                .get();
//        Double result = (double) ageStudent / numberOfStudent;

        Double scale = Math.pow(10, 2);
        return Math.ceil(result * scale) / scale;
    }

    public Collection<Student> getAllStudentsOnFaculty(Long id) {
        logger.info("Was invoked method for get all students on faculty");
        return List.copyOf(studentRepository.findStudentsByFaculty_Id(id));
    }

    public List<Student> findByAge(int age) {
        logger.info("Was invoked method for find students by age");
        return List.copyOf(studentRepository.findByAge(age));
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between values");
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public Faculty findFacultyByStudent(Long id) {
        logger.info("Was invoked method for find faculty by student");
        return findStudent(id).getFaculty();
    }

    public Integer getNumberOfStudents() {
        logger.info("Was invoked method for count all students");
        return studentRepository.countAllStudents();
    }

    public Double getMiddleAgeOfStudents() {
        logger.info("Was invoked method for get middle age of students");
        Double result = studentRepository.getMiddleAgeOfStudents();
        Double scale = Math.pow(10, 2);
        return Math.ceil(result * scale) / scale;
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return List.copyOf(studentRepository.getLastFiveStudents());
    }

    private void printStudentsRandom(int i) {
        List<Student> listStudents = List.copyOf(studentRepository.getAllInOrder());
        System.out.println(listStudents.get(i));
    }

    private synchronized void printStudentInOrder(int i) {
        List<Student> listStudents = List.copyOf(studentRepository.getAllInOrder());
        System.out.println(listStudents.get(i));
    }

    public void getSixStudentsOutOfOrder() {
        logger.info("Was invoked method for get six students out of order");
        Thread thread = new Thread(() -> {
            printStudentsRandom(2); //3
            printStudentsRandom(3); //4
        });
        Thread thread2 = new Thread(() -> {
            printStudentsRandom(4); //5
            printStudentsRandom(5); //6
        });
        printStudentsRandom(0); //1
        printStudentsRandom(1); //2
        thread.start();
        thread2.start();
    }

    public void getSixStudentsInOrder() {
        logger.info("Was invoked method for get six students in order");

        printStudentInOrder(0); //1
        printStudentInOrder(1); //2
        new Thread(()->{
            printStudentInOrder(2);
            printStudentInOrder(3);
        }).start();
        new Thread(()->{
            printStudentInOrder(4);
            printStudentInOrder(5);
        }).start();
    }

}
