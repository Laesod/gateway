package com.dto;

/**
 * Created by aautushk on 8/30/2015.
 */
public class UserResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean enabled;

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

    public Boolean getEnabled(){
        return this.enabled;
    }
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }
}
