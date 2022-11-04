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
        List<ProcessDataDAO> findAllByFname(String fname);
        List<ProcessDataDAO> findAllByLname(String lname);
        List<ProcessDataDAO> findAllByFilenumber(Integer filenumber);

        ProcessDataDAO findById(Integer id);


       // List<SystemUserDAO> findByOrderByUpvotesDesc();


}
