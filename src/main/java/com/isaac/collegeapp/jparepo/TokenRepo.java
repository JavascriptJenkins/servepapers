package com.isaac.collegeapp.jparepo;

import com.isaac.collegeapp.h2model.CancelTrainVO;
import com.isaac.collegeapp.h2model.TokenVO;
import com.isaac.collegeapp.model.TokenDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepo extends CrudRepository<TokenDAO, Long> {

    // todo: make this only grab tokens from last 24 hours
    List<TokenDAO> findAllByUsermetadata(String email);


    List<TokenDAO> findTop10ByUsermetadataOrderByCreatetimestampAsc(String email);



}
