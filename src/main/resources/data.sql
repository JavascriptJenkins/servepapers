DROP TABLE IF EXISTS process_data;

--CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;

CREATE TABLE process_data (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  fname VARCHAR(250) NOT NULL,
  mname VARCHAR(250) NOT NULL,
  lname VARCHAR(250) NOT NULL,
  age INT NOT NULL,
  actioncounter1 INT NOT NULL,
  actioncounter2 INT NOT NULL,
  notes VARCHAR(1000) NOT NULL, -- Note, find out what is largest size for this notes section
  address1 VARCHAR(500) NOT NULL,
  address2 VARCHAR(500) NOT NULL,
  race VARCHAR(500) NOT NULL,
  city VARCHAR(500) NOT NULL,
  state VARCHAR(500) NOT NULL,
  zipcode VARCHAR(500) NOT NULL,
  height VARCHAR(500) NOT NULL,
  haircolor VARCHAR(250) NOT NULL,
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
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    roles VARCHAR(250) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    isuseractive INT NOT NULL,
    updatedtimestamp DATE NOT NULL,
    createtimestamp DATE NOT NULL
);


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