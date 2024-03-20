package com.isaac.collegeapp.jparepo;

import com.isaac.collegeapp.model.ProcessDataDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessDataRepo extends CrudRepository<ProcessDataDAO, Long> {

        Page<ProcessDataDAO> findAll(Pageable pageable);
        List<ProcessDataDAO> findAll();
        ProcessDataDAO findByFname(String fname);
        List<ProcessDataDAO> findAllByFname(String fname);
        List<ProcessDataDAO> findAllByLname(String lname);
        List<ProcessDataDAO> findAllByFilenumber(Integer filenumber);
        ProcessDataDAO findById(Integer id);

}
