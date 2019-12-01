package com.reservo.reservo.ReturnClasses;

import com.reservo.reservo.Models.Roles;
import com.reservo.reservo.Repository.UserController;
import com.reservo.reservo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleReturn {

    @Autowired
    private UserRepository repository;

    private String userName;
    private String roles;

    public RoleReturn(String userName, String roles){
        this.userName = userName;
        this.roles = roles;
    }

    public RoleReturn(Roles role){
        UserController controller = new UserController(repository);
        this.userName = controller.getUser(role.getUserID()).get().getUserName();
        this.roles = role.getRoleString();
    }


    public String getUserName() { return userName; }

    public String getRoles() { return roles; }

}
