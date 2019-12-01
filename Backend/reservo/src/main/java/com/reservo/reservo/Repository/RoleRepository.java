package com.reservo.reservo.Repository;
import com.reservo.reservo.Models.Role;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends MongoRepository<Role, String>{

    Role findbyRole(String role);
}