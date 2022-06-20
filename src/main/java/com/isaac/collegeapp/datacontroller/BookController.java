package com.isaac.collegeapp.datacontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaac.collegeapp.model.BookDAO;
import com.isaac.collegeapp.model.BookDAO;
import com.isaac.collegeapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/book")
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAll")
    String getAllBooks() throws JsonProcessingException {

      return objectMapper.writeValueAsString(bookService.getAllBookData()) ;

    }

    @PostMapping("/create")
    String createBook(@RequestBody String book) throws JsonProcessingException {

        BookDAO bookDAO=objectMapper.readValue(book,BookDAO.class);

        String result=bookService.createBook(bookDAO);

        return result;

    }
    @PostMapping ("/update")
    String updateBook(@RequestBody String book) throws JsonProcessingException {

        BookDAO bookDAO = objectMapper.readValue(book, BookDAO.class);

        String result = bookService.updateBook(bookDAO);

        return result;
    }


}
