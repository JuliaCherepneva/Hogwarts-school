
SELECT student.name, student.age, faculty.name, faculty.color
FROM student
INNER JOIN faculty ON student.faculty_id = faculty.id


SELECT student.name, student.age, avatar.id FROM student
INNER JOIN avatar ON avatar.student_id = student.id.
