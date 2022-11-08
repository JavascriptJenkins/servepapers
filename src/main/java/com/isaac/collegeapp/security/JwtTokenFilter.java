package com.isaac.collegeapp.security;

import com.isaac.collegeapp.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter implements CsrfTokenRepository {

  private JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

      logger.info("REQUEST HIT JwtTokenFilter: "+httpServletRequest.getRequestURI());
      logger.info(" JwtTokenFilter: "+httpServletRequest.getMethod());

    System.out.println("PARAM ON FILTER: "+ httpServletRequest.getParameter("customJwtParameter"));
    String token = httpServletRequest.getParameter("customJwtParameter");


    System.out.println("############# HIT THE JWT TOKEN FILTER #####################");
    System.out.println("############# RESOLVING TOKEN: "+token);

    if("/login/logout".equals(httpServletRequest.getRequestURI())) {
        System.out.println("#############---->  LOGGING OUT");
    }


    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {

      String tokenSubject = jwtTokenProvider.getTokenSubject(token);
      logger.info("TOKEN VALIDATED WITH SUBJECT: "+tokenSubject);


        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
//      }

      } else if(httpServletRequest.getRequestURI().equals("/css/table.css")){
        logger.info("REQUEST FOR CSS");
      }
    } catch (CustomException ex) {
        logger.info("Exception on JWT controller: "+ex.getMessage());
      //this is very important, since it guarantees the user is not authenticated at all
      SecurityContextHolder.clearContext();
      httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
      return;
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  @Override
  public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
    System.out.println("############# GENERATE CSRF TOKEN #####################");
    return null;
  }

  @Override
  public void saveToken(CsrfToken csrfToken, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    System.out.println("############# SAVE CSRF TOKEN #####################");
  }

  @Override
  public CsrfToken loadToken(HttpServletRequest httpServletRequest) {


        System.out.println("############# LOADING CSRF TOKEN #####################");

        // String token = jwtTokenProvider.resolveToken(request); //need to modify this to pull out the crf token
        //    String token3 = request.getParameter("_csrf"); //need to modify this to pull out the crf token
        String token3 = jwtTokenProvider.resolveTokenFromCSFR(httpServletRequest);

        if(token3== null || token3.length() <10){
            System.out.println("############# CREATING DEFAULT TOKEN #####################");
            //if we have no token, return a default one with no priveldges
            String token1= jwtTokenProvider.createToken("default", (List<Role>) Arrays.asList(Role.ROLE_READ_ONLY));
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token1);
        }


        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token3);


  }
}
