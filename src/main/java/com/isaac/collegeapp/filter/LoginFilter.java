package com.isaac.collegeapp.filter;


import com.isaac.collegeapp.jparepo.StudentJpaRepo;
import com.isaac.collegeapp.model.StudentDAO;
import com.isaac.collegeapp.repo.StudentRepository;
import com.isaac.collegeapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        System.out.println("We are filtering the request now before it hits the controller. ");

        String credentials = httpServletRequest.getHeader("Authorization");

        if(credentials == null || credentials.isEmpty()){
            // this means we have no authorization, which means nobody has logged in yet
            System.out.println("credentials are null. ");
        } else {

            // check the database to see if the passed in credentials are legitimate
           // StudentDAO studentDAO = studentJpaRepo.findByStudent_NameAndStudent_ID_Number();


        }



     //   httpServletResponse.sendRedirect("/api/home/login");

        filterChain.doFilter(httpServletRequest, httpServletResponse); // we have to call this to keep filtering and trigger controller responses etc



    }
}
