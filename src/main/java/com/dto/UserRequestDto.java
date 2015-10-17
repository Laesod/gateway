package com.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by aautushk on 8/30/2015.
 */
public class UserRequestDto {

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    @Pattern(regexp=".+@.+\\.[a-z]+", message = "{NotValidEmailValue}")
    private String username;

    @NotNull(message ="{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String firstName;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String lastName;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    @Size(min = 6, message =  "{WrongPasswordLength}")
    private String password;

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String value){
        this.username = value;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String value){
        this.firstName = value;
    }

    public String getLastName(){
        return this.lastName;
    }
    public void setLastName(String value){
        this.lastName = value;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String value){
        this.password = value;
    }
}
