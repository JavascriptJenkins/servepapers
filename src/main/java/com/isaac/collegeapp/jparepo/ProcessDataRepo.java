package com.isaac.collegeapp.jparepo;

import com.isaac.collegeapp.model.ProcessDataDAO;
import com.isaac.collegeapp.model.SystemUserDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessDataRepo extends CrudRepository<ProcessDataDAO, Long> {

        List<ProcessDataDAO> findAll();
        ProcessDataDAO findByFname(String fname);
        ProcessDataDAO findById(Integer id);


       // List<SystemUserDAO> findByOrderByUpvotesDesc();


}
