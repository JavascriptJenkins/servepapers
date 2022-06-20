package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.model.RoomDAO;
import com.isaac.collegeapp.repo.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfessorBusinessLogicImpl implements ProfessorBL{

    @Autowired
    ProfessorRepository professorRepository;


    @Override
    public List<ProfessorDAO> filterPhdProfessors(List<ProfessorDAO> professorDAOList) {
        return null;
    }

    @Override
    public List<ProfessorDAO> getProfessorDAOList() {
        System.out.println("business logic imp is executing -3");

        return professorRepository.getProfessorDAOList();
    }

    @Override
    public ProfessorDAO getProfessorById(Integer professorid) {
        return null;
    }

    @Override
    public String updateProfessor(ProfessorDAO professorDAO) {
        return professorRepository.updateProfessor(professorDAO);
    }

    @Override
    public ProfessorDAO deleteProfessor(ProfessorDAO professorDAO) {
        return null;
    }

    @Override
    public String createProfessor(ProfessorDAO professorDAO) {

        if(professorDAO.getHas_phd().toString().length() == 1  && professorDAO.getHas_phd().equals(1)){

            return professorRepository.createProfessor(professorDAO);
        } else {

            return "professor must have phd, not inserting this record or your integer is too long";
        }

//

    }


}
