package com.reservo.reservo.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.reservo.reservo.Models.User;


@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    User findByUserName(String userName);
}