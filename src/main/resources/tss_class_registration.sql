CREATE TABLE tss.class_registration
(
    score int(11),
    status int(11),
    clazz_id bigint(20) NOT NULL,
    student_user_id varchar(10) NOT NULL,
    crid varchar(255) NOT NULL,
    confirm_time datetime,
    register_time datetime,
    class_id bigint(20) NOT NULL,
    student_id varchar(10) NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (clazz_id, student_user_id)
);
CREATE INDEX FK5wjj2ch9tcqu7014gv49xepie ON tss.class_registration (student_user_id);
CREATE INDEX FKpjd318gflj82248bsmigmppno ON tss.class_registration (class_id);
CREATE INDEX FKffe0ttchrsuieee092mfoexhp ON tss.class_registration (student_id);