package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by aautushk on 10/3/2015.
 */
public class ResetPasswordRequestDto {
    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "Field can not be empty")
    @Size(min = 6, message = "Password length should be at least 6 characters...")
    private String newPassword;

    public String getNewPassword(){
        return this.newPassword;
    }
    public void setNewPassword(String value){
        this.newPassword = value;
    }
}