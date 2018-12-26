package com.tmj.tms.utility;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorityChecker {

    public boolean hasRole(String role) {
        boolean result = false;
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            String userRole = authority.getAuthority();
            if (role.equals(userRole)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
