package com.isaac.collegeapp.model;

import java.sql.Date;

public class BuildingDAO {

    // These are your data attributes for the model object
    // Step 1)
    Integer building_id;
    String building_name;
    Date date_built;
    Integer capacity;
    String coordinates;

    public Integer getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Integer building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public Date getDate_built() {
        return date_built;
    }

    public void setDate_built(Date date_built) {
        this.date_built = date_built;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }


}
