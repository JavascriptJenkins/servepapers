package com.isaac.collegeapp.service;

import com.isaac.collegeapp.businesslogic.RoomBL;
import com.isaac.collegeapp.model.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomService {

    @Autowired
    RoomBL roomBL;

   public List<RoomDAO> getAllRoomData() {

       System.out.println("service layer is now executing-2");


        return roomBL.getRoomDAOList();
    }


    public String createRoom(RoomDAO roomDAO) {

        System.out.println("service layer is now executing-2");


        return roomBL.createRoom(roomDAO);
    }

    public String updateRoom(RoomDAO roomDAO) {

        System.out.println("service layer is now executing-2");


        return roomBL.updateRoom(roomDAO);
    }
}

