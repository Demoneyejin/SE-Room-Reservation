package com.reservo.reservo.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User{

    @Id
    private String userId;
    private String userName;
    private Map<String, String> userSettings = new HashMap<>();

    
    public final String getUserID(){
        return userId;
    }
    public final String getUserName(){
        return userName;
    }
    //UpdateUserName() from the model
    public void setUserName(final String name){
        userId = name;
    }
    //Required UpdateUserID in the model
    public void setUserID(final String Id){
       userId = Id;
    }

    public final Map<String, String> getUserSettings(){
        return userSettings;
    }
    public void setUserSettings(final Map<String, String> userSetting){
        this.userSettings = userSetting;
    }
}