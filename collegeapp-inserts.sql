INSERT INTO professor (professor_id, professor_name,hire_date,has_phd)
VALUES (106,'Mary Roth','2020/02/18',0);

INSERT INTO student (student_id, student_name,birthday,student_id_number)
Values (1, 'Sally W','2000/06/03', 481101830) ;


INSERT INTO book (book_id,book_name,edition,author,price,course_id)
VALUES (1,Harry_Potter,3,JK Rowling,16.50)



INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('1', '20', '102A', '1');
INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('2', '20', '506', '3');
INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('3', '100', '310', '4');
INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('4', '40', '204', '1');
INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('5', '12', 'B7', '3');
INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('6', '30', '207', '2');
INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('7', '45', '106', '3');

INSERT INTO `building` (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('1', 'Hunter Hall', '1913', '41 24.2028, 2 10.4418', '40');
INSERT INTO `building` (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('2', 'Main Hall', '1920', '41 24.2028, 2 10.4418', '300');
INSERT INTO `building` (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('3', 'Gil Hill Hall', '1996', '41 24.2028, 2 10.4418', '75');
INSERT INTO `building` (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('4', 'River Hall', '1962', '41 24.2028, 2 10.4418', '250');
INSERT INTO `building` (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('4', 'Arts Hall', '2015', '41 24.2028, 2 10.4418', '100');
INSERT INTO `building` (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('5', 'Sports Hall', '2021', '41 24.2028, 2 10.4418', '500');

-- todo: you must replace these course_id values with typical primary key integer values (1,2,3,4,...), and you must take your current course_id values and replace the unneeded redundant course_name values with these
INSERT INTO course (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('1', '7', 'MATH100', '4', 'Math Level 1 ');
INSERT INTO course (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('2', '2', 'MATH200', '4', 'Math Level 2 ');
INSERT INTO course (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('3', '4', 'SCIENCE100', '3', 'Science ');
INSERT INTO course (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('4', '1', 'BIOLOGY', '4', 'Biology');
INSERT INTO course (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('5', '3', 'ENGLISH', '3', 'English ');
INSERT INTO course (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('6', '6', 'BODY&SPORTS ', '2', 'Sports Level 1 ');

INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('1', 'Science 1 ', '2', 'Chris Miller', '20.99', 'SCIENCE101');
INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('2', 'Math 1', '3', 'Tim Ilcrest ', '20.99', 'MATH100');
INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('3', 'Math 2', '2', 'Heather Pike ', '20.99', 'MATH200');
INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('4', 'English for College', '7', 'Lily Collins', '20.99', 'ENGLISH');
INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('5', 'Athletic Mindset', '1', 'Thomas Brown ', '20.99', 'BODYWORKS');
INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('6', 'Biology ', '6', 'Mike Lindhall', '20.99', 'BIOLOGY');


-- todo: change the foreign key values in this table for course_id to reference the primary key values in the course table (1,2,3,4,5.....)
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('1', '6', '5');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('2', '2', '3');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('3', '2', '5');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('4', '4', '5');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('5', '5', '9');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('6', '6', '9');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('7', '6', '4');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('8', '4', '7');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('9', '2', '1');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('10','5', '9');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('11','1', '8');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('12','4', '6');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('13', '3', '5');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('14', '5', '1');
INSERT INTO `studentcourse` (`student_course_id`, `course_id`, `student_id`) VALUES ('15', '3', '4');

## todo: you have to drop
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('1', '106','3');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('2', '105','4');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('3', '104','1');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('4', '102','6');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('5', '101','2');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('6', '105','4');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('7', '103','1');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('8', '106','6');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('9', '106','4');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('10','104','1');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('11','103','2');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('12','102','5');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('13','101','2');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('14','103','6');
INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('15','102','2');


-- sample update statements below
UPDATE room
SET
    capacity = 35,
    room_number = 47,
    building_id = 4
WHERE
   room_id = 7;


