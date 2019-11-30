package com.reservo.reservo.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User{

    @Id
    private String passwordId;
    private String userName;
    private String userEmail;
    //contains the question and answer for security questions.
    private Map<String, String> securityScreening;
    private Map<String, String> userSettings = new HashMap<>();

    
    public final String getPasswordID(){
        return passwordId;
    }
    public final String getUserName(){
        return userName;
    }
    public final String getUserEmail(){
        return userEmail;
    }
    public final Map<String, String> getUserSettings(){
        return userSettings;
    }
    public final Map<String, String> getSecurityScreening(){
        return securityScreening;
    }
    //UpdateUserName() from the model
    public void setUserName(final String name){
        passwordId = name;
    }
    //Required UpdateUserID in the model
    public void setUserID(final String Id){
       passwordId = Id;
    }
    public void setUserEmail(final String email){
        userEmail = email;
    }
    public void setUserSettings(final Map<String, String> userSetting){
        this.userSettings = userSetting;
    }
    public void setSecurityScreening(final Map<String, String> securityScreening){
        this.securityScreening = securityScreening;
    }
}