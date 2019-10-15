package com.reservo.reservo.Repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jdk.internal.org.jline.utils.Log;

import com.reservo.reservo.Models.User;
import com.reservo.reservo.Repository.UserRepository;

@RestController
@RequestMapping(value="/")

public class UserController{
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        LOG.info("Getting All Users");
        return userRepository.findAll();
    }
    
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId){
        Log.info("Getting user with ID: {}.", userId);
        return userRepository.findOne(userId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUsers(@RequestBody User user){
        LOG.info("Saving user.");
        return userRepository.save(user);
    }


    

}