select *
from student
where age >= 11
  and age < 14;


select student.name
from student;


select student.name
from student
where name like '%г%';

select *
from student
where age < student.id;

select *
from student
order by age;

select *
from student
order by name;

select *
from faculty
order by id;

select *
from student, faculty
where student.faculty_id=faculty.id and student.id = 10;


