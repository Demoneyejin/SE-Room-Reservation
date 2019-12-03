package com.reservo.reservo.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Roles {

    @Id
    private String id;
    private String reservationID;
    private String userID;
    private List<String> roles;

    public Roles(String reservationID, String userID, List<String> roles){
        this.reservationID = reservationID;
        this.userID = userID;
        this.roles = roles;
    }

    public String getReservationID() { return reservationID; }

    public String getUserID() { return userID; }

    public List<String> getRoles(){ return roles; }

    public String getRoleString(){
        StringBuilder s = new StringBuilder();
        roles.forEach(role -> s.append(role).append(", "));
        return s.toString();
    }

    public void addRole(String role){ this.roles.add(role); }

    public String getRoleID(){ return id; }
}
