package com.isaac.collegeapp.jparepo;

import com.isaac.collegeapp.h2model.CancelTrainVO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancelTrainRepo extends CrudRepository<CancelTrainVO, Long> {

        List<CancelTrainVO> findByLname(String lname);

        List<CancelTrainVO> findByOrderByUpvotesDesc();


}
