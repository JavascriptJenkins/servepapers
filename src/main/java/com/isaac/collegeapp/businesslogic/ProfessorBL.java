package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.ProfessorDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProfessorBL {

   public List<ProfessorDAO> filterPhdProfessors (List<ProfessorDAO> professorDAOList);
   public List<ProfessorDAO> getProfessorDAOList ();
   public ProfessorDAO getProfessorById(Integer professorid);
   public  String updateProfessor(ProfessorDAO professorDAO);
   public  ProfessorDAO deleteProfessor(ProfessorDAO professorDAO);
   public String createProfessor(ProfessorDAO professorDAO);

}
