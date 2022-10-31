package com.isaac.collegeapp.util;

import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.model.SystemUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

// cache to get all user data by passing in a user object
@Component
public class SimpleCache {

    @Autowired
    SystemUserRepo systemUserRepo;

    // hold mapping of users and roles
    protected HashMap<SystemUserDAO, String> usermap;

    public void setUserMapCache (HashMap<SystemUserDAO, String> usermap){
        this.usermap = usermap;
    }

    // making a cache of the users and their roles so we don't have to go to database every time
    public void refreshCache(){

        List<SystemUserDAO> systemUsers = systemUserRepo.findAll();

        HashMap<SystemUserDAO, String> usermap = new HashMap<>();

        // for each database row add it to the cache
        for(SystemUserDAO systemUser : systemUsers){
            usermap.put(systemUser, systemUser.getRoles()); // add the users and their roles to the map
        }

        setUserMapCache(usermap); // set the in-memory cache with values from database
    }


}
