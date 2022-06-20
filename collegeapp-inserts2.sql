ISELECT * FROM studentcourse

 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('1', '6', '5');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('2', '2', '3');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('3', '2', '5');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('4', '4', '5');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('5', '5', '9');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('6', '6', '9');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('7', '6', '4');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('8', '4', '7');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('9', '2', '1');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('10','5', '9');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('11','1', '8');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('12','4', '6');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('13', '3', '5');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('14', '5', '1');
 INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('15', '3', '4');


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


  INSERT INTO `collegeapp`.`course` (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('1', '7', 'MATH', '4', 'Math Level 1 ');
 INSERT INTO `collegeapp`.`course` (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('2', '2', 'MATH', '4', 'Math Level 2 ');
 INSERT INTO `collegeapp`.`course` (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('3', '4', 'SCIENCE', '3', 'Science ');
 INSERT INTO `collegeapp`.`course` (`course_id`, `room_id`, `course_name`, `creds`, `descri ption`) VALUES ('4', '1', 'BIOLOGY', '4', 'Biology');
 INSERT INTO `collegeapp`.`course` (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('5', '3', 'ENGLISH', '3', 'English ');
 INSERT INTO `collegeapp`.`course` (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('6', '6', 'BODY&SPORTS', '2', 'Sports Level 1 ');

 ALTER TABLE book
 ADD COLUMN book_name VARCHAR(225) AFTER book_id;

 ALTER TABLE book
 DROP COLUMN course_name



 SELECT * FROM professorcourse

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

 INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('1', 'Science 1', '2', 'Chris Miller', '20.99', '3');
 INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('2', 'Math 1', '3', 'Tim Ilcrest ', '20.99', '1');
 INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('3', 'Math 2', '2', 'Heather Pike ', '20.99', '2');
 INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('4', 'English for College', '7', 'Lily Collins', '20.99', '5');
 INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('5', 'Athletic Mindset', '1', 'Thomas Brown ', '20.99', '6');
 INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('6', 'Biology ', '6', 'Mike Lindhall', '20.99', '4');



 INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('1', '20', '102', '1');
 INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('2', '20', '506', '3');
 INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('3', '100', '310', '4');
 INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('4', '40', '204', '1');
 INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('5', '12', '7', '3');
 INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('6', '30', '207', '2');
 INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('7', '45', '106', '3');




 INSERT INTO course (course_id, room_id,course_name, creds,course_id_desc)
 VALUES (1, 7,'math',4,'Mathematics')

  INSERT INTO course (course_id, room_id,course_name, creds,course_id_desc) VALUES (1, 7,'math',4,'Mathematics');
 INSERT INTO course (course_id, room_id,course_name, creds,course_id_desc) VALUES ('2', '2', 'MATH', '4', 'Math Level 2 ');
 INSERT INTO course (course_id, room_id,course_name, creds,course_id_desc) VALUES ('3', '4', 'SCIENCE', '3', 'Science ');
 INSERT INTO course (course_id, room_id,course_name, creds,course_id_desc) VALUES ('4', '1', 'BIOLOGY', '4', 'Biology');
 INSERT INTO course (course_id, room_id,course_name, creds,course_id_desc) VALUES ('5', '3', 'ENGLISH', '3', 'English ');
 INSERT INTO course (course_id, room_id,course_name, creds,course_id_desc) VALUES ('6', '6', 'BODY&SPORTS', '2', 'Sports Level 1 ');

 INSERT INTO building (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('1', 'Hunter Hall', '1913-08-19', '41 24.2028, 2 10.4418', '40');
 INSERT INTO building (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('2', 'Main Hall', '1920-05-12', '41 24.2028, 2 10.4418', '300');
 INSERT INTO building (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('3', 'Gil Hill Hall', '1996-11-10', '41 24.2028, 2 10.4418', '75');
 INSERT INTO building (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('4', 'River Hall', '1962-09-11', '41 24.2028, 2 10.4418', '250');
 INSERT INTO building (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('5', 'Arts Hall', '2015-07-05', '41 24.2028, 2 10.4418', '100');
 INSERT INTO building (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('6', 'Sports Hall', '2021-06-05', '41 24.2028, 2 10.4418', '500');

 INSERT INTO `collegeapp`.`course` (`course_id`, `room_id`, `course_name`, `creds`, `description`) VALUES ('1', '7', 'MATH', 'MATH4', 'Math Level 1 ');
