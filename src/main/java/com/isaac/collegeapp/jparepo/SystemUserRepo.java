package com.isaac.collegeapp.jparepo;

import com.isaac.collegeapp.model.SystemUserDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemUserRepo extends CrudRepository<SystemUserDAO, Long> {

        List<SystemUserDAO> findAll();
        SystemUserDAO findByEmail(String email);
        SystemUserDAO findById(Integer id);


       // List<SystemUserDAO> findByOrderByUpvotesDesc();


}
