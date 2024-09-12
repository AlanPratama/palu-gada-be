package com.palu_gada_be.palu_gada_be.security;

import com.palu_gada_be.palu_gada_be.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {
    public boolean isUser(Authentication authentication, Long userId){
        if (authentication == null || !authentication.isAuthenticated()){
            return false;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User)) {
            return false;
        }

        User user = (User) principal;

        return user.getId() == userId;
    }
}
