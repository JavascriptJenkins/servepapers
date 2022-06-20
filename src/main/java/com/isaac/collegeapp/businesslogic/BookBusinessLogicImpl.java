package com.isaac.collegeapp.businesslogic;

import com.isaac.collegeapp.model.BookDAO;
import com.isaac.collegeapp.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookBusinessLogicImpl implements BookBL{

    @Autowired
    BookRepository bookRepository;


    @Override
    public List<BookDAO> filterPhdBooks(List<BookDAO> bookDAOList) {
        return null;
    }

    @Override
    public List<BookDAO> getBookDAOList() {
        System.out.println("business logic imp is executing -3");

        return bookRepository.getBookDAOList();
    }

    @Override
    public BookDAO getBookById(Integer bookid) {
        return null;
    }

    @Override
    public String updateBook(BookDAO bookDAO) {
        return bookRepository.updateBook(bookDAO);
    }

    @Override
    public BookDAO deleteBook(BookDAO bookDAO) {
        return null;
    }

    @Override
    public String createBook(BookDAO bookDAO) {
        return bookRepository.createBook(bookDAO);
    }
}
