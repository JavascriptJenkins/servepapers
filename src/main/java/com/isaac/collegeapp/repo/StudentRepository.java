package com.isaac.collegeapp.repo;

import com.isaac.collegeapp.model.StudentDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentRepository {




    public String updateStudent(StudentDAO studentDAO){

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");

            String sql = "UPDATE student SET student_name= ?, birthday= ?, student_id_number= ? WHERE student_id = ?";


//            student (student_id INT NOT NULL,
//                    student_name VARCHAR (225),
//                    birthday DATE,
//                    student_id_number INT,
//                    PRIMARY KEY (student_id))


            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, studentDAO.getStudentName());
            statement.setDate(2, studentDAO.getBirthday());
            statement.setInt(3, studentDAO.getStudentIDNumber());
            statement.setInt(4, studentDAO.getStudentID());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new student was updated successfully!");
            }

        } catch(Exception exception){

            System.out.println("caught exception: "+exception.getMessage());

            return "problem updating student: " +exception.getMessage();

        }

        return"success updating student";

    }


    public String createStudent(StudentDAO studentDAO){
        Connection conn = null;
        int existingMaxPrimaryKey = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");



            // execute sql query to get the max id so we can increment primary key
            String getprimarykeymax = "SELECT MAX(STUDENT_ID) as student_id_max FROM STUDENT;";
            PreparedStatement getPrimaryKeyStatement = conn.prepareStatement(getprimarykeymax);
            ResultSet primaryKeyResult = getPrimaryKeyStatement.executeQuery();
            while(primaryKeyResult.next()){
                existingMaxPrimaryKey = primaryKeyResult.getInt("student_id_max");
            }




            String sql = "INSERT INTO Student (student_id, student_name,birthday,student_id_number) VALUES (?, ?, ?, ?)";
            // INSERT INTO student (student_id, student_name,birthday,student_id_number) VALUES ('1', '20', '102', '1');

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, existingMaxPrimaryKey +1); // this probably gonna throw a null pointer becuase on create statements we dont use a PRIMARY KEY
            statement.setString(2, studentDAO.getStudentName());
            statement.setDate(3, studentDAO.getBirthday());
            statement.setInt(4, studentDAO.getStudentIDNumber());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new student was inserted successfully!");
            }


        } catch (Exception exception){

            System.out.println("caught exception: "+exception.getMessage());
            return "problem inserting student: "+exception.getMessage();

        }


        return "Successfully inserted new student!";

    }


    public List<StudentDAO> getStudentDAOList() {

        System.out.println("now in reo layer fetching data-4");

        // initializing two variables
        Connection conn = null;
        List<StudentDAO> studentDAOList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");


            // Do something with the Connection
            String select = "SELECT * from student";
            PreparedStatement selectstudents = conn.prepareStatement(select);
            ResultSet resultSet = selectstudents.executeQuery();
            System.out.println (resultSet);
            studentDAOList=parseResultSet(resultSet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return studentDAOList;
    }

    List<StudentDAO> parseResultSet(ResultSet resultSet) {

        List<StudentDAO> studentDAOList=new ArrayList<>();

        try{
            while(resultSet.next()) {

                // on the right hand side is the mysql column name
                // on the left hand side is the java "Set" method
                StudentDAO studentDAO=new StudentDAO();
                studentDAO.setStudentID(resultSet.getInt("student_id"));
                studentDAO.setStudentName(resultSet.getString("student_name"));
                studentDAO.setBirthday(resultSet.getDate("birthday"));
                studentDAO.setStudentIDNumber(resultSet.getInt("student_id_number"));
                studentDAOList.add(studentDAO);
            }

        } catch (SQLException ex ) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return studentDAOList;
    }

}
