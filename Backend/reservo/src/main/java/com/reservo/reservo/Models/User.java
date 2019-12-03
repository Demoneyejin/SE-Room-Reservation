package com.reservo.reservo.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

@Document
public class User{

    @Id
    private String password;
    private String userName;
    private String userEmail;
    private String fullName;
    //used to store security type id
    private String userID;
    //contains the question and answer for security questions.
    private Map<String, String> securityScreening = new HashMap<>();
    //contains roles for the user, simplified here instead of being an object.
    private Map<String, String> userSettings = new HashMap<>();

    public User(){}

	public User(String id, String username, String password){
        this.userID = id;
        this.userName = username;
        this.password = password;
    }
    
    public final String getPassword(){
        return password;
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
    public final String getUserID(){
        return userID;
    }
   //Required UpdateUserID in the model
   public void setUserID(String Id){
        userID = Id;
    }
    public final String getFullName(){
        return this.fullName;
    }
    public void setFullName(String name){
        this.fullName = name;
    }
    //UpdateUserName() from the model
    public void setUserName(final String name){
        this.userName = name;
    }
    //Required Update PAssword in the model
    public void setUserPassword(final String Id){
       password = Id;
    }
    public void setUserEmail(final String email){
        userEmail = email;
    }
    public void setUserSettings(final Map<String, String> userSetting){
        this.userSettings = userSetting;
    }
    public void appendUserSettings(String Key, String Value){
        this.userSettings.put(Key, Value);
    }
    public void setSecurityScreening(final Map<String, String> securityScreening){
        this.securityScreening = securityScreening;
    }

}