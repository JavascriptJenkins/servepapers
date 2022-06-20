package com.isaac.collegeapp.viewcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/bookView")
@Controller
public class BookViewController {

    @Autowired
    BookService bookService;

    ObjectMapper objectMapper = new ObjectMapper();



    @GetMapping("/viewBooks")
    String viewBooks(Model model){

        model.addAttribute("books", bookService.getAllBookData());
        return "viewBooks.html";
    }




}
