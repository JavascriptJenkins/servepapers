package com.isaac.collegeapp.service;

import com.isaac.collegeapp.businesslogic.ProfessorBL;
import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.model.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfessorService {

    @Autowired
    ProfessorBL professorBL;

   public List<ProfessorDAO> getAllProfessorData() {

       System.out.println("service layer is now executing-2");


        return professorBL.getProfessorDAOList();
    }



    public String createProfessor(ProfessorDAO professorDAO) {

        System.out.println("service layer is now executing-2");


        return professorBL.createProfessor(professorDAO);
    }
    public String updateProfessor(ProfessorDAO professorDAO) {

        System.out.println("service layer is now executing-2");


        return professorBL.updateProfessor(professorDAO);
    }


}
