DROP TABLE IF EXISTS process_data;
DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS systemuser;

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;

CREATE TABLE processdata (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  fname VARCHAR(250) NOT NULL,
  filenumber INT NOT NULL,
  mname VARCHAR(250) NOT NULL,
  lname VARCHAR(250) NOT NULL,
  age INT NOT NULL,
  actioncounter1 INT NOT NULL,
  serveattempts INT NOT NULL,
  notes VARCHAR(1000), -- Note, find out what is largest size for this notes section
  address1 VARCHAR(200) NOT NULL,
  address2 VARCHAR(100),
  race VARCHAR(20) NOT NULL,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(100) NOT NULL,
  zipcode VARCHAR(10) NOT NULL,
  heightinches INT NOT NULL,
  heightfeet INT NOT NULL,
  haircolor VARCHAR(20) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  lastattemptvisit TIMESTAMP NOT NULL,
  updatedtimestamp TIMESTAMP NOT NULL,
  createtimestamp TIMESTAMP NOT NULL
  );

--CREATE TABLE cancel_train (
--  id INT AUTO_INCREMENT  PRIMARY KEY,
--  fname VARCHAR(250) NOT NULL,
--  lname VARCHAR(250) NOT NULL,
--  iscanceled BOOLEAN NOT NULL,
--  upvotes INT NOT NULL,
--  downvotes INT NOT NULL,
--  cancelstatus INT NOT NULL,
--  why VARCHAR(500) NOT NULL,
--  imageurl VARCHAR(250) NOT NULL,
--  updatedtimestamp TIMESTAMP NOT NULL,
--  createtimestamp TIMESTAMP NOT NULL
--  );
--
--

-- token table is used for sending tokens to emails for login/reset password
CREATE TABLE token (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    token VARCHAR(250) NOT NULL,
    usermetadata VARCHAR(250) NOT NULL,
    tokenused INT NOT NULL,
    updatedtimestamp DATE NOT NULL,
    createtimestamp DATE NOT NULL
);

-- systemuser is a user of the system
CREATE TABLE systemuser (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    email VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    roles ARRAY NOT NULL,
    phone VARCHAR(20) NOT NULL,
    isuseractive INT NOT NULL,
    updatedtimestamp TIMESTAMP NOT NULL,
    createtimestamp TIMESTAMP NOT NULL
);

   INSERT INTO systemuser (email,
                            password,
                            roles,
                            phone,
                            isuseractive,
                            updatedtimestamp,
                            createtimestamp) VALUES
     ('javascriptjenkins@gmail.com','$2a$10$yQoju5RnDO/WfxO6xFcddOzurPBWPOnThM6bwpM98GMCLMiZ7dr/q','READ,WRITE,','6128007774',1,'1970-01-01 12:12:12','1970-01-01 12:12:12');



--   INSERT INTO cancel_train (fname,
--                            lname,
--                            iscanceled,
--                            upvotes,
--                            downvotes,
--                            cancelstatus,
--                            why,
--                            imageurl,
--                            updatedtimestamp,
--                            createtimestamp) VALUES
--     ('Dave','Evans',true,20,10,10,'did something bad','/images/myimage1','1970-01-01 12:12:12','1970-01-01 12:12:12'),
--     ('Steve','Evans',false,30,10,30,'did something real bad','/images/myimage2','1970-01-01 12:12:12','1970-01-01 12:12:12'),
--     ('Alex','Evans',true,50,10,90,'did something extra bad','/images/myimage3','1970-01-01 12:12:12','1970-01-01 12:12:12');

-- INSERT INTO real_estate (fname,
--                          lname,
--                          yrblt,
--                          isnew,
--                          sqft,
--                          baths,
--                          beds,
--                          street,
--                          city,
--                          state,
--                          zip,
--                          listing) VALUES
--   ('Dave','Evans','2018-06-06T22:45:52.247Z',true,6200,7,4,'1484 Massa Ct','Collierville','TN',38027,'MIDSOUTH RESIDENTIAL LLC'),
--   ('Steve','Evans','2018-06-06T22:45:52.247Z',true,6200,7,4,'1484 Massa Ct','Collierville','TN',38027,'MIDSOUTH RESIDENTIAL LLC'),
--   ('Bill','Evans','2018-06-06T22:45:52.247Z',true,6200,7,4,'1484 Massa Ct','Collierville','TN',38027,'MIDSOUTH RESIDENTIAL LLC');



-- SQL script for generating test data recrods - run via H2 console
-- Insert data into PROCESSDATA table
--INSERT INTO PROCESSDATA (ID, ACTIONCOUNTER1, ADDRESS1, ADDRESS2, AGE, CITY, CREATETIMESTAMP, FILENUMBER, FNAME, HAIRCOLOR, HEIGHTFEET, HEIGHTINCHES, LASTATTEMPTVISIT, LNAME, MNAME, NOTES, PHONE, RACE, SERVEATTEMPTS, STATE, UPDATEDTIMESTAMP, ZIPCODE)
--SELECT
--    NEXTVAL('HIBERNATE_SEQUENCE'),
--    FLOOR(RAND() * 100),
--    CONCAT('Address1_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CONCAT('Address2_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    FLOOR(RAND() * 100),
--    CONCAT('City_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CURRENT_TIMESTAMP,
--    FLOOR(RAND() * 10000),
--    CONCAT('First_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CASE WHEN RAND() < 0.5 THEN 'Brown' ELSE 'Blonde' END,
--    FLOOR(RAND() * 10),
--    FLOOR(RAND() * 12),
--    CURRENT_TIMESTAMP,
--    CONCAT('Last_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CONCAT('Middle_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CONCAT('Notes_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CONCAT('123-456-', LPAD(FLOOR(RAND() * 10000), 4, '0')),
--    CASE WHEN RAND() < 0.5 THEN 'White' ELSE 'Black' END,
--    FLOOR(RAND() * 100),
--    CONCAT('State_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CURRENT_TIMESTAMP,
--    CONCAT('ZIP_', LPAD(FLOOR(RAND() * 100), 2, '0'))
--FROM
--    INFORMATION_SCHEMA.TABLES
--LIMIT 100;
--
---- Insert data into SYSTEMUSER table
--INSERT INTO SYSTEMUSER (ID, CREATETIMESTAMP, EMAIL, ISUSERACTIVE, NAME, PASSWORD, PHONE, PROJECT, UPDATEDTIMESTAMP)
--SELECT
--    NEXTVAL('HIBERNATE_SEQUENCE'),
--    CURRENT_TIMESTAMP,
--    CONCAT('user_', LPAD(FLOOR(RAND() * 100), 2, '0'), '@example.com'),
--    FLOOR(RAND() * 2),
--    CONCAT('Name_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CONCAT('password_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CONCAT('123-456-', LPAD(FLOOR(RAND() * 10000), 4, '0')),
--    CONCAT('Project_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    CURRENT_TIMESTAMP
--FROM
--    INFORMATION_SCHEMA.TABLES
--LIMIT 100;
--
---- Insert data into SYSTEM_USERDAO_ROLES table
--INSERT INTO SYSTEM_USERDAO_ROLES (SYSTEM_USERDAO_ID, ROLES, ROLES_ORDER)
--SELECT
--    ID,
--    FLOOR(RAND() * 10),
--    FLOOR(RAND() * 10)
--FROM
--    SYSTEMUSER
--LIMIT 100;
--
---- Insert data into TOKEN table
--INSERT INTO TOKEN (ID, CREATETIMESTAMP, TOKEN, TOKENUSED, UPDATEDTIMESTAMP, USERMETADATA)
--SELECT
--    NEXTVAL('HIBERNATE_SEQUENCE'),
--    CURRENT_TIMESTAMP,
--    CONCAT('Token_', LPAD(FLOOR(RAND() * 100), 2, '0')),
--    FLOOR(RAND() * 2),
--    CURRENT_TIMESTAMP,
--    CONCAT('UserMetadata_', LPAD(FLOOR(RAND() * 100), 2, '0'))
--FROM
--    INFORMATION_SCHEMA.TABLES
--LIMIT 100;