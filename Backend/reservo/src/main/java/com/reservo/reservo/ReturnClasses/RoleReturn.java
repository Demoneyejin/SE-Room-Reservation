package com.reservo.reservo.ReturnClasses;

import com.reservo.reservo.Models.Roles;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RoleReturn {

    @Autowired
    private UserRepository repository;

    private String userName;
    private String roles;

    public RoleReturn(String userName, String roles){
        this.userName = userName;
        this.roles = roles;
    }

    public RoleReturn(Roles role, UserRepository repository){
        Optional<User> userOp = repository.findById(role.getUserID());
        userOp.ifPresent(user -> this.userName = user.getUserName());
        this.roles = role.getRoleString();
    }


    public String getUserName() { return userName; }

    public String getRoles() { return roles; }

}
