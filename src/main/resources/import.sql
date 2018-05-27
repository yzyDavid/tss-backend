insert into department (id, dept_name) values (0, 'Computer Science');
insert into course (id, credit, intro, name, num_lessons_each_week, department_id) values ('20011', 2.0, 'An Description', 'Data Structure', 5, 0);
insert into class (id, capacity, num_student, semester, year, course_id, teacher_id) values (10001, 100, 50, 'FIRST', 2017, '20011', 'root');
insert into class (id, capacity, num_student, semester, year, course_id, teacher_id) values (10002, 100, 50, 'FIRST', 2017, '20011', 'root');
insert into campus (id, name) values (1, 'Yuquan');
insert into building (id, name, campus_id) values (1, '7th Building', 1);
insert into classroom (id, capacity, name, building_id) values (1, 120, '101', 1);
insert into time_slot (id, type, classroom_id, class_id) values (1, 'MON_1_2', 1, 10001);
insert into user(user_id, hashed_pwd, salt, group_id) values('123456','4THeO+ajZ3plyHqj3yEKmulDBiCMvF5+utIA49X6Usg=', 'Fwh+3BmSRRGH5ypv9CZjFA==', 1);