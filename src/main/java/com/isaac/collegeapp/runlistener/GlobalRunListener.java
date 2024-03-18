package com.isaac.collegeapp.runlistener;

import com.isaac.collegeapp.jparepo.SystemUserRepo;
import com.isaac.collegeapp.util.SimpleCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GlobalRunListener implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    SystemUserRepo systemUserRepo;

    @Autowired
    SimpleCache simpleCache;



    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("----- TechVVS Application has started ----");
        System.out.println("------- TechVVS Custom Cache Init ------");
        //System.out.println(passwordEncoder.encode("password"));

        simpleCache.refreshCache();

        //loadDatabaseConnection();
        //getProfessors();
    }

    protected void loadDatabaseConnection() {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            System.out.println("load database method is happening");

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

    }

}
