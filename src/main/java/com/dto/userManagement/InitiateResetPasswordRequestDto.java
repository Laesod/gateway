package com.dto.userManagement;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by aautushk on 11/24/2015.
 */

public class InitiateResetPasswordRequestDto {
    @NotNull(message = "{FieldCanNotBeEmpty}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    @Pattern(regexp=".+@.+\\.[a-z]+", message = "{NotValidEmailValue}")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}