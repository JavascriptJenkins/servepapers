package com.isaac.collegeapp.repo;

import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.model.RoomDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorRepository {



    public String updateProfessor(ProfessorDAO professorDAO){

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");

            String sql = "UPDATE professor SET professor_name= ?, hire_date= ?, has_phd= ? WHERE professor_id= ?";


//            UPDATE room
//            SET
//                    capacity = 35,
//                    room_number = 47,
//                    building_id = 4
//            WHERE
//                    room_id = 7;

//            professor_id INT NOT NULL,
//            professor_name VARCHAR(225),
//                    hire_date DATE ,
//                    has_phd INT,
//                    PRIMARY KEY  (professor_id))


            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, professorDAO.getProfessor_name());
            statement.setDate(2, professorDAO.getHire_date());
            statement.setInt(3, professorDAO.getHas_phd());
            statement.setInt(4, professorDAO.getProfessor_id());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new professor was updated successfully!");
            }

        } catch(Exception exception){

            System.out.println("caught exception: "+exception.getMessage());

            return "problem updating professor: " +exception.getMessage();

        }

        return"success updating professor";

    }

    public String createProfessor(ProfessorDAO professorDAO){
        Connection conn = null;
        int existingMaxPrimaryKey = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");



            // execute sql query to get the max id so we can increment primary key
            String getprimarykeymax = "SELECT MAX(PROFESSOR_ID) as professor_id_max FROM PROFESSOR;";
            PreparedStatement getPrimaryKeyStatement = conn.prepareStatement(getprimarykeymax);
            ResultSet primaryKeyResult = getPrimaryKeyStatement.executeQuery();
            while(primaryKeyResult.next()){
                existingMaxPrimaryKey = primaryKeyResult.getInt("professor_id_max");
            }




            String sql = "INSERT INTO Professor (professor_id, professor_name,hire_date,has_phd) VALUES (?, ?, ?, ?)";
            // INSERT INTO professor (professor_id, professor_name,hire_date,has_phd) VALUES (106,'Mary Roth','2020/02/18',0);

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, existingMaxPrimaryKey +1); // this probably gonna throw a null pointer becuase on create statements we dont use a PRIMARY KEY
            statement.setString(2, professorDAO.getProfessor_name());
            statement.setDate(3, professorDAO.getHire_date());
            statement.setInt(4, professorDAO.getHas_phd());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new professor was inserted successfully!");
            }


        } catch (Exception exception){

            System.out.println("caught exception: "+exception.getMessage());
        }


        return "success";

    }

    public List<ProfessorDAO> getProfessorDAOList() {

        System.out.println("now in reo layer fetching data-4");

        // initializing two variables
        Connection conn = null;
        List<ProfessorDAO> professorDAOList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false&allowPublicKeyRetrieval=true");


            // Do something with the Connection
            String select = "SELECT * from professor";
            PreparedStatement selectprofessors = conn.prepareStatement(select);
            ResultSet resultSet = selectprofessors.executeQuery();
            System.out.println (resultSet);
            professorDAOList=parseResultSet(resultSet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return professorDAOList;
    }

    List<ProfessorDAO> parseResultSet(ResultSet resultSet) {

        List<ProfessorDAO> professorDAOList=new ArrayList<>();

        try{
            while(resultSet.next()) {

                ProfessorDAO professorDAO=new ProfessorDAO();
                professorDAO.setProfessor_id(resultSet.getInt("professor_id"));
                professorDAO.setProfessor_name(resultSet.getString("professor_name"));
                professorDAO.setHire_date(resultSet.getDate("hire_date"));
                professorDAO.setHas_phd(resultSet.getInt("has_phd"));
                professorDAOList.add(professorDAO);
            }

        } catch (SQLException ex ) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return professorDAOList;
    }

}
