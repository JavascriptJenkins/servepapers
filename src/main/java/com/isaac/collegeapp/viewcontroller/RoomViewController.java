package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/roomView")
@Controller
public class RoomViewController {

    @Autowired
    RoomService roomService;

    ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("/viewRooms")
    String viewRooms(Model model){

        model.addAttribute("rooms", roomService.getAllRoomData());
        return "viewRooms.html";
    }

    @GetMapping("/viewRealEstate")
    String viewRealEstate(){

        return "viewRealEstate.html";
    }




}
