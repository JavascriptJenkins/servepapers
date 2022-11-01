package com.isaac.collegeapp.security;

import org.springframework.security.core.GrantedAuthority;

// These are the roles that are inside of the issued JWTs
public enum Role implements GrantedAuthority {
  ROLE_ADMIN, ROLE_CLIENT, ROLE_READ_ONLY, DELIVERY_DRIVER, EMPLOYEE, CHAT_USER;

  public String getAuthority() {
    return name();
  }

}
