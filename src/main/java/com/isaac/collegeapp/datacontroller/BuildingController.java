package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.BuildingDAO;
import com.isaac.collegeapp.model.BuildingDAO;
import com.isaac.collegeapp.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/building")
@RestController
public class BuildingController {

    @Autowired
    BuildingService buildingService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllBuildings() throws JsonProcessingException {

      return objectMapper.writeValueAsString(buildingService.getAllBuildingData()) ;

    }



    @PostMapping("/create")
    String createBuilding(@RequestBody String building) throws JsonProcessingException {

        BuildingDAO buildingDAO=objectMapper.readValue(building, BuildingDAO.class);

        String result=buildingService.createBuilding(buildingDAO);

        return result;

    }

    @PostMapping ("/update")
    String updateBuilding(@RequestBody String building) throws JsonProcessingException {

        BuildingDAO buildingDAO = objectMapper.readValue(building, BuildingDAO.class);

        String result = buildingService.updateBuilding(buildingDAO);

        return result;
    }

}
