package com.reservo.reservo.DAL;

import com.reservo.reservo.Models.Role;

public interface Role_DAL{

    Role getRoleById(String id);
    Role findbyRole(String role);
}