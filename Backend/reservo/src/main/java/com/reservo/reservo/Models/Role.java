package com.reservo.reservo.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class Role{

    @Id
    private String Id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups= true)

    private String role;

    public String getID(){
        return this.Id;
    }
    public void setID(String Id){
        this.Id = Id;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

}