package com.isaac.collegeapp.repo;

import com.isaac.collegeapp.model.ProfessorCourseDAO;
import com.isaac.collegeapp.model.ProfessorCourseDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorCourseRepository {


    // This is a class level variable because it was initialized outside of all the methods below
    String sticks = "stuff";

    // does professorcourse need to be camel case?


    public String updateProfessorCourse(ProfessorCourseDAO professorcourseDAO) {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");

            String sql = "UPDATE professorcourse SET professor_id= ?, course_id= ? WHERE professor_course_id = ?";


//            professor_course_id INT NOT NULL,
//            professor_id INT,
//            course_id INT,


            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, professorcourseDAO.getProfessor_id());
            statement.setInt(2, professorcourseDAO.getCourse_id());
            statement.setInt(3, professorcourseDAO.getProfessor_course_id());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new professorcourse was updated successfully!");
            }

        } catch (Exception exception) {

            System.out.println("caught exception: " + exception.getMessage());

            return "problem updating professorcourse: " + exception.getMessage();

        }

        return "success updating professorcourse";
    }

    public String createProfessorCourse(ProfessorCourseDAO professorcourseDAO){
        Connection conn = null;
        int existingMaxPrimaryKey = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");



            // execute sql query to get the max id so we can increment primary key
            String getprimarykeymax = "SELECT MAX(PROFESSOR_COURSE_ID) as professor_course_id_max FROM PROFESSORCOURSE;";
            PreparedStatement getPrimaryKeyStatement = conn.prepareStatement(getprimarykeymax);
            ResultSet primaryKeyResult = getPrimaryKeyStatement.executeQuery();
            while(primaryKeyResult.next()){
                existingMaxPrimaryKey = primaryKeyResult.getInt("professor_Course_id_max");
            }




            String sql = "INSERT INTO ProfessorCourse (professor_course_id, professor_id, course_id) VALUES (?, ?, ?)";
            // INSERT INTO professorcourse (`professor_course_id`, `professor_id`, `course_id`) VALUES ('1', '106','3');

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, existingMaxPrimaryKey +1); // this probably gonna throw a null pointer becuase on create statements we dont use a PRIMARY KEY
            statement.setInt(2, professorcourseDAO.getProfessor_id());
            statement.setInt(3, professorcourseDAO.getCourse_id());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new ProfessorCourse was inserted successfully!");
            }


        } catch (Exception exception){

            System.out.println("caught exception: "+exception.getMessage());
        }


        return "success";

    }








    public List<ProfessorCourseDAO> getProfessorCourseDAOList() {

        // this is a method level variable because it was initialized inside the method
        String sticks2 = "stuff";

        // System.out.println is a logging statement that prints out data in the console
        System.out.println("now in reo layer fetching data-4");

        // initializing two variables
        Connection conn = null;
        List<ProfessorCourseDAO> professorcourseDAOList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");


            // Do something with the Connection
            String select = "SELECT * from professorcourse";
            PreparedStatement selectprofessorcourses = conn.prepareStatement(select);
            ResultSet resultSet = selectprofessorcourses.executeQuery();
            System.out.println (resultSet);
            professorcourseDAOList=parseResultSet(resultSet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return professorcourseDAOList;
    }

    List<ProfessorCourseDAO> parseResultSet(ResultSet resultSet) {


        List<ProfessorCourseDAO> professorcourseDAOList=new ArrayList<>();

        try{
            while(resultSet.next()) {

                ProfessorCourseDAO professorcourseDAO=new ProfessorCourseDAO();
                professorcourseDAO.setProfessor_course_id(resultSet.getInt("professor_course_id"));
                professorcourseDAO.setProfessor_id(resultSet.getInt("professor_id"));
                professorcourseDAO.setCourse_id(resultSet.getInt("course_id"));
                professorcourseDAOList.add(professorcourseDAO);
            }

        } catch (SQLException ex ) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return professorcourseDAOList;
    }

}
