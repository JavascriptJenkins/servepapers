package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.model.ProfessorDAO;
import com.isaac.collegeapp.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/professorView")
@Controller
public class ProfessorViewController {

    @Autowired
    ProfessorService professorService;

    ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("/viewProfessors")
    String viewProfessors(Model model){

        model.addAttribute("professors", professorService.getAllProfessorData());
        return "viewProfessors.html";
    }

    @GetMapping("/editProfessor")
    String editProfessor(Model model){
        model.addAttribute("professor", new ProfessorDAO());
        model.addAttribute("professors",professorService.getAllProfessorData());
        return "editProfessor.html";
    }

    @GetMapping("/newProfessor")
    String newProfessor(Model model){
        model.addAttribute("professor", new ProfessorDAO());
        model.addAttribute("professors",professorService.getAllProfessorData());
        return "newProfessor.html";
    }


    @PostMapping("/createProfessor")
    String createProfessor(@ModelAttribute( "professor" ) ProfessorDAO professorDAO, Model model){

        // Validation on professor name
        if(professorDAO.getProfessor_name().length() > 60){
            model.addAttribute("errorMessage","Professor Name must be shorter than 60 characters");
        } else {
            // This code block will execute if there are no errors in the data that was inputed by the client

            // step 1) create the new professor and attach the success message
            String result = professorService.createProfessor(professorDAO);

            if(!result.equals("success")){
                model.addAttribute("errorMessage",result);
            } else {
                model.addAttribute("successMessage",result);
            }



            // step 2) fetch the list of professors from the database and bind the list onto the page
            model.addAttribute("professors",professorService.getAllProfessorData());

        }

        return "newProfessor.html";
    }


}
