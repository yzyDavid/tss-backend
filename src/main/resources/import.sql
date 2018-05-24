INSERT INTO department (id, dept_name) VALUES (0, 'Computer Science');
INSERT INTO course (id, credit, intro, name, num_lessons_each_week, department_id) VALUES ('20011', 2.0, 'An Description', 'Data Structure', 5, 0);
INSERT INTO class (id, capacity, num_student, semester, year, course_id, teacher_id) VALUES (10001, 100, 50, 'FIRST', 2017, '20011', 'root');
INSERT INTO campus (id, name) VALUES (1, 'Yuquan');
INSERT INTO building (id, name, campus_id) VALUES (1, '7th Building', 1);
INSERT INTO classroom (id, capacity, name, building_id) VALUES (1, 120, '101', 1);