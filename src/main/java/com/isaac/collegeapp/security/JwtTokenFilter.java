package com.isaac.collegeapp.security;

import com.isaac.collegeapp.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

  private JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
      logger.info("REQUEST HIT JwtTokenFilter: "+httpServletRequest.getRequestURI());
      logger.info(" JwtTokenFilter: "+httpServletRequest.getMethod());
    String token = jwtTokenProvider.resolveToken(httpServletRequest);
    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {

      String tokenSubject = jwtTokenProvider.getTokenSubject(token);
      logger.info("TOKEN VALIDATED WITH SUBJECT: "+tokenSubject);

        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      } else if ("/api/ws".equals(httpServletRequest.getRequestURI())){
        logger.info("Web Socket request Came in, lettin er' thru na");

        token = jwtTokenProvider.resolveTokenFromWebSocket(httpServletRequest);
        String tokenSubject = jwtTokenProvider.getTokenSubject(token);
        logger.info("Web Socket TOKEN VALIDATED WITH SUBJECT: "+tokenSubject);

        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        httpServletResponse.setStatus(101); // when connecting, ws protocol needs to return 101

      }
    } catch (CustomException ex) {
      //this is very important, since it guarantees the user is not authenticated at all
      SecurityContextHolder.clearContext();
      httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
      return;
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

}
