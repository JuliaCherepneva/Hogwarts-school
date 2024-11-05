
SELECT student.name, student.age, faculty.name, faculty.color
FROM student
INNER JOIN faculty ON student.faculty_id = faculty


SELECT student.name, student.age, avatar.id FROM student
INNER JOIN student ON avatar.id = student
