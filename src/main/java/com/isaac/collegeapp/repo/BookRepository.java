package com.isaac.collegeapp.repo;

import com.isaac.collegeapp.model.BookDAO;
import com.isaac.collegeapp.model.BookDAO;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookRepository {





    public String updateBook(BookDAO bookDAO) {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");

            String sql = "UPDATE book SET book_name= ?, edition= ?, author= ?, price= ?, course_id= ? WHERE book_id = ?";


//            (book_id INT NOT NULL,
//                    book_name INT,
//                    edition INT,
//                    author VARCHAR (225),
//                    price DOUBLE,
//                    course_id INT,


            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, bookDAO.getBook_name());
            statement.setString(2, bookDAO.getEdition());
            statement.setString(3, bookDAO.getAuthor());
            statement.setDouble(4, bookDAO.getPrice());
            statement.setString(5, bookDAO.getCourse_id());
            statement.setInt(6, bookDAO.getBook_id());


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was updated successfully!");
            }

        } catch (Exception exception) {

            System.out.println("caught exception: " + exception.getMessage());

            return "problem updating book: " + exception.getMessage();

        }

        return "success updating book";

    }
    public String createBook(BookDAO bookDAO){
        Connection conn = null;
        int existingMaxPrimaryKey = 0;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");



            // execute sql query to get the max id so we can increment primary key
            String getprimarykeymax = "SELECT MAX(BOOK_ID) as book_id_max FROM BOOK;";
            PreparedStatement getPrimaryKeyStatement = conn.prepareStatement(getprimarykeymax);
            ResultSet primaryKeyResult = getPrimaryKeyStatement.executeQuery();
            while(primaryKeyResult.next()){
                existingMaxPrimaryKey = primaryKeyResult.getInt("book_id_max");
            }




            String sql = "INSERT INTO Book (book_id, book_name, edition, author, price, course_id) VALUES (?, ?, ?, ?, ?, ?)";
            // INSERT INTO book (`book_id`, `book_name`, `edition`, `author`, `price`, `course_id`) VALUES ('1', 'Science 1', '2', 'Chris Miller', '20.99', '3');

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, existingMaxPrimaryKey +1); // this probably gonna throw a null pointer becuase on create statements we dont use a PRIMARY KEY
            statement.setString(2, bookDAO.getBook_name());
            statement.setString(3, bookDAO.getEdition());
            statement.setString(4, bookDAO.getAuthor());
            statement.setDouble(5, bookDAO.getPrice());
            statement.setString(6, bookDAO.getCourse_id());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was inserted successfully!");
            }


        } catch (Exception exception){

            System.out.println("caught exception: "+exception.getMessage());
        }


        return "success";

    }

    public List<BookDAO> getBookDAOList() {

        System.out.println("now in reo layer fetching data-4");

        // initializing two variables
        Connection conn = null;
        List<BookDAO> bookDAOList = new ArrayList<>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/collegeapp?user=root&password=password&useSSL=false");


            // Do something with the Connection
            String select = "SELECT * from book";
            PreparedStatement selectbooks = conn.prepareStatement(select);
            ResultSet resultSet = selectbooks.executeQuery();
            System.out.println (resultSet);
            bookDAOList=parseResultSet(resultSet);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return bookDAOList;
    }

    List<BookDAO> parseResultSet(ResultSet resultSet) {

        List<BookDAO> bookDAOList=new ArrayList<>();

        try{
            while(resultSet.next()) {

                BookDAO bookDAO=new BookDAO();
                bookDAO.setBook_id(resultSet.getInt("book_id"));
                bookDAO.setBook_name(resultSet.getString("book_name"));
                bookDAO.setEdition(resultSet.getString("edition"));
                bookDAO.setCourse_id(resultSet.getString("course_id"));
                bookDAOList.add(bookDAO);
            }


        } catch (SQLException ex ) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return bookDAOList;
    }

}
