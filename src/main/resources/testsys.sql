-- remove user init by yzy.
-- insert into user(user_id, hashed_pwd, salt, group_id) values('123456','4THeO+ajZ3plyHqj3yEKmulDBiCMvF5+utIA49X6Usg=', 'Fwh+3BmSRRGH5ypv9CZjFA==', 1);
insert into question(question_qid, question_answerednum, question_correct, question_qanswer, question_qtype, question_question, question_qunit) values ('1', '0', '0', '1', '1', 'Is monkey an animal?', 'animal');
insert into question(question_qid, question_answerednum, question_correct, question_qanswer, question_qtype, question_question, question_qunit) values ('2', '0', '0', '0', '1', 'Is apple an animal?', 'animal');
insert into question(question_qid, question_answerednum, question_correct, question_qanswer, question_qtype, question_question, question_qunit) values ('3', '0', '0', '4', '3', '1+3 = ?', 'maths');
insert into paper(paper_pid, paper_answerednum, paper_average, paper_begin, paper_count, paper_end, paper_isauto, paper_last, paper_papername)values ('1', '0', '0', '2018-06-02 14:00:00', '2', '2018-06-03 14:00:00', 0, '1:00:00', 'test1');
insert into contains(contains_id, contains_score, paper_pid, question_qid) values ('10000', '10', '1', '1');
insert into contains(contains_id, contains_score, paper_pid, question_qid) values ('10001', '90', '1', '2');
insert into paper(paper_pid, paper_answerednum, paper_average, paper_begin, paper_count, paper_end, paper_isauto, paper_last, paper_papername)values ('2', '0', '0', '2018-06-03 14:00:00', '2', '2018-06-04 14:00:00', 1, '1:30:00', 'test2');
insert into contains(contains_id, contains_score, paper_pid, question_qid) values ('20000', '20', '2', '1');
insert into contains(contains_id, contains_score, paper_pid, question_qid) values ('20001', '80', '2', '2');

-- insert into user(user_id, hashed_pwd, salt, group_id) values('3150100001','4THeO+ajZ3plyHqj3yEKmulDBiCMvF5+utIA49X6Usg=', 'Fwh+3BmSRRGH5ypv9CZjFA==', 4);
-- insert into user(user_id, hashed_pwd, salt, group_id) values('2100100000','4THeO+ajZ3plyHqj3yEKmulDBiCMvF5+utIA49X6Usg=', 'Fwh+3BmSRRGH5ypv9CZjFA==', 3);

