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
    //used to store security type id
    private ObjectId _id;
    //contains the question and answer for security questions.
    private Map<String, String> securityScreening = new HashMap<>();
    //contains roles for the user, simplified here instead of being an object.
    private Map<String, String> userSettings = new HashMap<>();

    public User(){}

	public User(ObjectId id, String username, String password){
        _id = id;
        userName = username;
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
    public final String get_Id(){
        return this._id.toHexString();
    }
  
    //UpdateUserName() from the model
    public void setUserName(final String name){
        password = name;
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
    public void set_Id(ObjectId id){
        this._id = id;
    }
}