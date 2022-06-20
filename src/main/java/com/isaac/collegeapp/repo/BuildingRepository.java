package com.isaac.collegeapp.repo;

import com.isaac.collegeapp.model.BuildingDAO;
import com.isaac.collegeapp.model.BuildingDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuildingRepository {


    public String updateBuilding(BuildingDAO buildingDAO){

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");

            String sql = "UPDATE building SET building_name= ?, date_built= ?, coordinates= ?, capacity= ? WHERE building_id = ?";


//            (building_id INT NOT NULL,
//                    building_name INT ,
//                    date_built DATE,
//                    coordinates VARCHAR (225) ,
//                    capacity INT,
//                    PRIMARY KEY (building_id))
            ;


            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,  buildingDAO.getBuilding_name());
            statement.setDate(2, buildingDAO.getDate_built());
            statement.setString(3, buildingDAO.getCoordinates());
            statement.setInt(4, buildingDAO.getCapacity());
            statement.setInt(5, buildingDAO.getBuilding_id());



            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new building was updated successfully!");
            }

        } catch(Exception exception){

            System.out.println("caught exception: "+exception.getMessage());

            return "problem updating building: " +exception.getMessage();

        }

        return"success updating building";

    }
    

    public String createBuilding(BuildingDAO buildingDAO){
        Connection conn = null;
        int existingMaxPrimaryKey = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");



            // execute sql query to get the max id so we can increment primary key
            String getprimarykeymax = "SELECT MAX(BUILDING_ID) as building_id_max FROM BUILDING;";
            PreparedStatement getPrimaryKeyStatement = conn.prepareStatement(getprimarykeymax);
            ResultSet primaryKeyResult = getPrimaryKeyStatement.executeQuery();
            while(primaryKeyResult.next()){
                existingMaxPrimaryKey = primaryKeyResult.getInt("building_id_max");
            }




            String sql = "INSERT INTO Building (building_id,building_name,date_built,coordinates,capacity) VALUES (?, ?, ?, ?, ?, ?)";
            // INSERT INTO building (`building_id`, `building_name`, `date_built`, `coordinates`, `capacity`) VALUES ('1', 'Hunter Hall', '1913-08-19', '41 24.2028, 2 10.4418', '40');

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, existingMaxPrimaryKey +1); // this probably gonna throw a null pointer becuase on create statements we dont use a PRIMARY KEY
            statement.setString(2, buildingDAO.getBuilding_name());
            statement.setDate(3, buildingDAO.getDate_built());
            statement.setString(4, buildingDAO.getCoordinates());
            statement.setInt(5, buildingDAO.getCapacity());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new building was inserted successfully!");
            }


        } catch (Exception exception){

            System.out.println("caught exception: "+exception.getMessage());
        }


        return "success";

    }



    public List<BuildingDAO> getBuildingDAOList() {

        System.out.println("now in reo layer fetching data-4");

        // initializing two variables
        Connection conn = null;
        List<BuildingDAO> buildingDAOList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");


            // Do something with the Connection
            String select = "SELECT * from building";
            PreparedStatement selectbuildings = conn.prepareStatement(select);
            ResultSet resultSet = selectbuildings.executeQuery();
            System.out.println (resultSet);
            buildingDAOList=parseResultSet(resultSet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return buildingDAOList;
    }

    List<BuildingDAO> parseResultSet(ResultSet resultSet) {

        List<BuildingDAO> buildingDAOList=new ArrayList<>();

        try{
            while(resultSet.next()) {

                BuildingDAO buildingDAO=new BuildingDAO();
                buildingDAO.setBuilding_id(resultSet.getInt("building_id"));
                buildingDAO.setBuilding_name(resultSet.getString("building_name"));
                buildingDAO.setDate_built(resultSet.getDate("date_built"));
                buildingDAO.setCoordinates(resultSet.getString("coordinates"));
                buildingDAO.setCapacity(resultSet.getInt("capacity"));
                buildingDAOList.add(buildingDAO);
            }

        } catch (SQLException ex ) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return buildingDAOList;
    }

}
