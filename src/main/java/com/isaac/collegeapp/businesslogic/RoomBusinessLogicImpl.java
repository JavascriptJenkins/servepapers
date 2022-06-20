package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.RoomDAO;
import com.isaac.collegeapp.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomBusinessLogicImpl implements RoomBL{

    @Autowired
    RoomRepository roomRepository;


    @Override
    public List<RoomDAO> filterPhdRooms(List<RoomDAO> roomDAOList) {
        return null;
    }

    @Override
    public List<RoomDAO> getRoomDAOList() {
        System.out.println("business logic imp is executing -3");

        return roomRepository.getRoomDAOList();
    }

    @Override
    public RoomDAO getRoomById(Integer roomid) {
        return null;
    }

    @Override
    public String updateRoom(RoomDAO roomDAO) {
        return roomRepository.updateRoom(roomDAO);

    }

    @Override
    public RoomDAO deleteRoom(RoomDAO roomDAO) {
        return null;
    }

    @Override
    public String createRoom(RoomDAO roomDAO) {
        return roomRepository.createRoom(roomDAO);
    }
}
