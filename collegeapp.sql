CREATE TABLE student (student_id INT NOT NULL,
                      student_name VARCHAR (225),
                      birthday DATE,
                      student_id_number INT,
                      PRIMARY KEY (student_id))
;
CREATE TABLE professor ( professor_id INT NOT NULL,
                         professor_name VARCHAR(225),
                         hire_date DATE ,
                         has_phd INT,
                         PRIMARY KEY  (professor_id))

;

CREATE TABLE building  (building_id INT NOT NULL,
                        building_name INT ,
                        date_built DATE,
                        coordinates VARCHAR (225) ,
                        capacity INT,
                        PRIMARY KEY (building_id))
;

CREATE TABLE room (room_id INT NOT NULL,
                   capacity INT,
                   room_number INT,
                   building_id INT,
                   PRIMARY KEY (room_id),
                   FOREIGN KEY (building_id) REFERENCES building(building_id))
;


CREATE TABLE course (course_id INT NOT NULL,
                     room_id int NOT NULL,
                     course_name VARCHAR (225),
                     creds INT,
                     course_id_desc VARCHAR (225),
                     PRIMARY KEY (course_id),
                     FOREIGN KEY (room_id) REFERENCES room (room_id)

);

CREATE TABLE book (book_id INT NOT NULL,
                   book_name INT,
                   edition INT,
                   author VARCHAR (225),
                   price DOUBLE,
                   course_id INT,
                   PRIMARY KEY (book_id),
                   FOREIGN KEY (course_id) REFERENCES course(course_id))
;

CREATE TABLE professorcourse ( professor_course_id INT NOT NULL,
                               professor_id INT,
                               course_id INT,
                               PRIMARY KEY (professor_course_id),
                               FOREIGN KEY (professor_id)REFERENCES professor(professor_id),
                               FOREIGN KEY (course_id)REFERENCES course(course_id))
;
CREATE TABLE studentcourse ( student_course_id INT NOT NULL,
                             course_id INT,
                             student_id INT,
                             PRIMARY KEY (student_course_id) ,
                             FOREIGN KEY (course_id)REFERENCES course(course_id),
                             FOREIGN KEY (student_id)REFERENCES student(student_id))



;