package com.isaac.collegeapp.repo;

import com.isaac.collegeapp.model.StudentCourseDAO;
import com.isaac.collegeapp.model.StudentCourseDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentCourseRepository {


    public String updateStudentCourse(StudentCourseDAO studentcourseDAO) {


        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");

            String sql = "UPDATE studentcourse SET course_id= ?, student_id= ? WHERE student_course_id = ?";




//            student_course_id INT NOT NULL,
//            course_id INT,
//            student_id INT,

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, studentcourseDAO.getCourse_id());
            statement.setInt(2, studentcourseDAO.getStudent_id());
            statement.setInt(3, studentcourseDAO.getStudent_course_id());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new studentcourse was updated successfully!");
            }

        } catch (Exception exception) {

            System.out.println("caught exception: " + exception.getMessage());

            return "problem updating studentcourse: " + exception.getMessage();

        }

        return "success updating studentcourse";
    }


    public String createStudentCourse(StudentCourseDAO studentcourseDAO){
        Connection conn = null;
        int existingMaxPrimaryKey = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");



            // execute sql query to get the max id so we can increment primary key
            String getprimarykeymax = "SELECT MAX(STUDENT_COURSE_ID) as student_course_id_max FROM STUDENTCOURSE;";
            PreparedStatement getPrimaryKeyStatement = conn.prepareStatement(getprimarykeymax);
            ResultSet primaryKeyResult = getPrimaryKeyStatement.executeQuery();
            while(primaryKeyResult.next()){
                existingMaxPrimaryKey = primaryKeyResult.getInt("student_course_id_max");
            }




            String sql = "INSERT INTO STUDENTCOURSE (student_course_id, course_id, student_id) VALUES (?, ?, ?)";
            // INSERT INTO studentcourse (`student_course_id`, `course_id`, `student_id`) VALUES ('1', '6', '5');;

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, existingMaxPrimaryKey +1); // this probably gonna throw a null pointer becuase on create statements we dont use a PRIMARY KEY
            statement.setInt(2, studentcourseDAO.getCourse_id());
            statement.setInt(3, studentcourseDAO.getStudent_id());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new studentcourse was inserted successfully!");
            }


        } catch (Exception exception){

            System.out.println("caught exception: "+exception.getMessage());
        }


        return "success";

    }



    public List<StudentCourseDAO> getStudentCourseDAOList() {

        System.out.println("now in reo layer fetching data-4");

        // initializing two variables
        Connection conn = null;
        List<StudentCourseDAO> studentcourseDAOList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");


            // Do something with the Connection
            String select = "SELECT * from studentcourse";
            PreparedStatement selectstudentcourses = conn.prepareStatement(select);
            ResultSet resultSet = selectstudentcourses.executeQuery();
            System.out.println (resultSet);
            studentcourseDAOList=parseResultSet(resultSet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return studentcourseDAOList;
    }

    List<StudentCourseDAO> parseResultSet(ResultSet resultSet) {

        List<StudentCourseDAO> studentcourseDAOList=new ArrayList<>();

        try{
            while(resultSet.next()) {

                StudentCourseDAO studentcourseDAO=new StudentCourseDAO();
                studentcourseDAO.setStudent_course_id(resultSet.getInt("student_course_id"));
                studentcourseDAO.setCourse_id(resultSet.getInt("course_id"));
                studentcourseDAO.setStudent_id(resultSet.getInt("student_id"));
                studentcourseDAOList.add(studentcourseDAO);
            }
        } catch (SQLException ex ) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return studentcourseDAOList;
    }

}