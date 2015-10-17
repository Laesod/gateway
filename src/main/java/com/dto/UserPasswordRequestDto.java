package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by aautushk on 9/5/2015.
 */
public class UserPasswordRequestDto {
    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "Field can not be empty")
    private String currentPassword;

    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "Field can not be empty")
    @Size(min = 6, message = "Password length should be at least 6 characters...")
    private String newPassword;

    public String getCurrentPassword(){
        return this.currentPassword;
    }
    public void setCurrentPassword(String value){
        this.currentPassword = value;
    }

    public String getNewPassword(){
        return this.newPassword;
    }
    public void setNewPassword(String value){
        this.newPassword = value;
    }
}
