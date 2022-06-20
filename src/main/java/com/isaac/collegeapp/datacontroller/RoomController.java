package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.RoomDAO;
import com.isaac.collegeapp.service.BookService;
import com.isaac.collegeapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/room")
@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllRooms() throws JsonProcessingException {

      return objectMapper.writeValueAsString(roomService.getAllRoomData());

    }

    @PostMapping ("/create")
    String createRoom(@RequestBody String room) throws JsonProcessingException {

        RoomDAO roomDAO=objectMapper.readValue(room,RoomDAO.class);

        String result=roomService.createRoom(roomDAO);

        return result;

    }

    @PostMapping ("/update")
    String updateRoom(@RequestBody String room) throws JsonProcessingException {

        RoomDAO roomDAO = objectMapper.readValue(room, RoomDAO.class);

        String result = roomService.updateRoom(roomDAO);

        return result;
    }


    }
