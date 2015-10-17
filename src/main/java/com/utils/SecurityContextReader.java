package com.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by aautushk on 9/20/2015.
 */

public class SecurityContextReader {
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null){
                return authentication.getName();
            }else{
                return "";
            }
        }
}
