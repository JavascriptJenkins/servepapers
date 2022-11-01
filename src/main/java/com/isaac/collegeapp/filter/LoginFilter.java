//package com.isaac.collegeapp.filter;
//
//
//import com.isaac.collegeapp.jparepo.StudentJpaRepo;
//import com.isaac.collegeapp.model.StudentDAO;
//import com.isaac.collegeapp.repo.StudentRepository;
//import com.isaac.collegeapp.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class LoginFilter extends OncePerRequestFilter {
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//
//
//        System.out.println("We are filtering the request now before it hits the controller. ");
//
//        String credentials = httpServletRequest.getHeader("Authorization");
//
//        if(credentials == null
//                || credentials.isEmpty()
//        ){
//            // this means we have no authorization, which means nobody has logged in yet
//            System.out.println("credentials are null. redirect to auth page");
//            httpServletResponse.sendRedirect ("auth"); // redirect to the auth page is there are no credentials to parse
//
//        } else {
//
//            // check to make sure the authorization header is in the valid format (length check)
//
//
//            // use secret key to decrypt username/password
//
//            // check against the cache to update roles (make a cache/cache update service)
//
//            filterChain.doFilter(httpServletRequest, httpServletResponse); // we have to call this to keep filtering and trigger controller responses etc
//
//        }
//
//
//
//     //   httpServletResponse.sendRedirect("/api/home/login");
//
//
//    }
//}
