package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.BookDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookBL {

   public List<BookDAO> filterPhdBooks (List<BookDAO> bookDAOList);
   public List<BookDAO> getBookDAOList ();
   public BookDAO getBookById(Integer bookid);
   public  String updateBook(BookDAO bookDAO);
   public  BookDAO deleteBook(BookDAO bookDAO);
   public String createBook(BookDAO bookDAO);

}
