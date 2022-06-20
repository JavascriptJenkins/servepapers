package com.isaac.collegeapp.repo;

import com.isaac.collegeapp.model.RoomDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoomRepository {






    public String updateRoom(RoomDAO roomDAO){

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");

            String sql = "UPDATE room SET capacity= ?, room_number= ?, building_id= ? WHERE room_id = ?";


//            UPDATE room
//            SET
//                    capacity = 35,
//                    room_number = 47,
//                    building_id = 4
//            WHERE
//                    room_id = 7;


            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,  roomDAO.getCapacity());
            statement.setInt(2, roomDAO.getRoom_number());
            statement.setInt(3, roomDAO.getBuilding_id());
            statement.setInt(4, roomDAO.getRoom_id());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new room was updated successfully!");
            }

        } catch(Exception exception){

            System.out.println("caught exception: "+exception.getMessage());

            return "problem updating room: " +exception.getMessage();

        }

        return"success updating room";

    }


    public String createRoom(RoomDAO roomDAO){
        Connection conn = null;
        int existingMaxPrimaryKey = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");



            // execute sql query to get the max id so we can increment primary key
            String getprimarykeymax = "SELECT MAX(ROOM_ID) as room_id_max FROM ROOM;";
            PreparedStatement getPrimaryKeyStatement = conn.prepareStatement(getprimarykeymax);
            ResultSet primaryKeyResult = getPrimaryKeyStatement.executeQuery();
            while(primaryKeyResult.next()){
                existingMaxPrimaryKey = primaryKeyResult.getInt("room_id_max");
            }




            String sql = "INSERT INTO Room (room_id, capacity, room_number, building_id) VALUES (?, ?, ?, ?)";
           // INSERT INTO room (`room_id`, `capacity`, `room_number`, `building_id`) VALUES ('1', '20', '102', '1');

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, existingMaxPrimaryKey +1); // this probably gonna throw a null pointer becuase on create statements we dont use a PRIMARY KEY
            statement.setInt(2, roomDAO.getCapacity());
            statement.setInt(3, roomDAO.getRoom_number());
            statement.setInt(4, roomDAO.getBuilding_id());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new room was inserted successfully!");
            }


        } catch (Exception exception){

            System.out.println("caught exception: "+exception.getMessage());
        }


        return "success";

    }

    public List<RoomDAO> getRoomDAOList() {

        System.out.println("now in reo layer fetching data-4");

        // initializing two variables
        Connection conn = null;
        List<RoomDAO> roomDAOList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");


            // Do something with the Connection
            String select = "SELECT * from room";
            PreparedStatement selectrooms = conn.prepareStatement(select);
            ResultSet resultSet = selectrooms.executeQuery();
            System.out.println (resultSet);
            roomDAOList=parseResultSet(resultSet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return roomDAOList;
    }

    List<RoomDAO> parseResultSet(ResultSet resultSet) {

        List<RoomDAO> roomDAOList=new ArrayList<>();

        try{
            while(resultSet.next()) {

                RoomDAO roomDAO=new RoomDAO();
                roomDAO.setRoom_id(resultSet.getInt("room_id"));
                roomDAO.setCapacity(resultSet.getInt("capacity"));
                roomDAO.setRoom_number(resultSet.getInt("room_number"));
                roomDAO.setBuilding_id(resultSet.getInt("building_id"));
                roomDAOList.add(roomDAO);
            }

        } catch (SQLException ex ) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return roomDAOList;
    }

}
