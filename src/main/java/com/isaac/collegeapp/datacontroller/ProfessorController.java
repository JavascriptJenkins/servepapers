package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.model.RoomDAO;
import com.isaac.collegeapp.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/professor")
@RestController
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllProfessors() throws JsonProcessingException {

      return objectMapper.writeValueAsString(professorService.getAllProfessorData()) ;

    }

    @PostMapping("/create")
    String createProfessor(@RequestBody String professor) throws JsonProcessingException {

        ProfessorDAO professorDAO=objectMapper.readValue(professor,ProfessorDAO.class);

        String result=professorService.createProfessor(professorDAO);

        return result;

    }

    @PostMapping ("/update")
    String updateProfessor(@RequestBody String professor) throws JsonProcessingException {

        ProfessorDAO professorDAO = objectMapper.readValue(professor,ProfessorDAO.class);

        String result = professorService.updateProfessor(professorDAO);

        return result;
    }



}
