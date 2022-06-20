package com.isaac.collegeapp.service;

import com.isaac.collegeapp.businesslogic.BuildingBL;
import com.isaac.collegeapp.model.BuildingDAO;
import com.isaac.collegeapp.model.BuildingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildingService {

    @Autowired
    BuildingBL buildingBL;

   public List<BuildingDAO> getAllBuildingData() {

       System.out.println("service layer is now executing-2");


        return buildingBL.getBuildingDAOList();
    }
    public String createBuilding(BuildingDAO buildingDAO) {

        System.out.println("service layer is now executing-2");


        return buildingBL.createBuilding(buildingDAO);
    }
    public String updateBuilding(BuildingDAO buildingDAO) {

        System.out.println("service layer is now executing-2");


        return buildingBL.updateBuilding(buildingDAO);
    }

}
