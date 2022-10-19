SELECT s.name, s.age, f.name
FROM student s
FULL JOIN faculty f ON s.faculty_id = f.id;

SELECT s.id, s.name, s.age, a.id, a.media_type
FROM student s
INNER JOIN avatar a on s.id = a.student_id