package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/buildingView")
@Controller
public class BuildingViewController {

    @Autowired
    BuildingService buildingService;

    ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("/viewBuildings")
    String viewBuildings(Model model){

        model.addAttribute("buildings", buildingService.getAllBuildingData());
        return "viewBuildings.html";
    }




}
