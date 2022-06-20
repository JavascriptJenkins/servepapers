package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.BuildingDAO;
import com.isaac.collegeapp.repo.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildingBusinessLogicImpl implements BuildingBL{

    @Autowired
    BuildingRepository buildingRepository;


    @Override
    public List<BuildingDAO> filterPhdBuildings(List<BuildingDAO> buildingDAOList) {
        return null;
    }

    @Override
    public List<BuildingDAO> getBuildingDAOList() {
        System.out.println("business logic imp is executing -3");

        return buildingRepository.getBuildingDAOList();
    }

    @Override
    public BuildingDAO getBuildingById(Integer buildingid) {
        return null;
    }

    @Override
    public String updateBuilding(BuildingDAO buildingDAO) {
        return buildingRepository.updateBuilding(buildingDAO);
    }

    @Override
    public BuildingDAO deleteBuilding(BuildingDAO buildingDAO) {
        return null;
    }

    @Override
    public String createBuilding(BuildingDAO buildingDAO) {
        return buildingRepository.createBuilding(buildingDAO);
    }
}
