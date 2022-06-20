package com.isaac.collegeapp.jparepo;

import com.isaac.collegeapp.h2model.CancelTrainVO;
import com.isaac.collegeapp.h2model.TokenVO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepo extends CrudRepository<TokenVO, Long> {

    TokenVO findByToken(String token);

}
