package com.reservo.reservo.Repository;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/users")

public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        LOG.info("Getting All Users");
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Optional<User> getUser(@PathVariable String userId) {
        Log.info("Getting user with ID: {}.", userId);
        return userRepository.findById(userId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUsers(@RequestBody User user) {
        LOG.info("Saving user.");
        return userRepository.save(user);
    }

    @RequestMapping(value = "/settings/{userId}", method = RequestMethod.GET)
    public Object getAllUserSettings(@PathVariable String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user != null) {
            User Localuser = user.get();
            return Localuser.getUserSettings();
        } else {
            return "User not found.";
        }
    }

    @RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
    public String getUserSetting(@PathVariable String userId, @PathVariable String key) {
        Optional<User> user = userRepository.findById(userId);
        if(user  != null)
        {
            User Localuser = user.get();
            return Localuser.getUserSettings().get(key);
        }else{
            return "User not found.";
        }
    }

    @RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
    public String addUserSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) 
    {
	    Optional<User> user = userRepository.findById(userId);
	    if (user != null) {
            User Localuser = user.get();
		    Localuser.getUserSettings().put(key, value);
		    userRepository.save(Localuser);
		    return "Key added";
        } 
        else {
		return "User not found.";
	    }       
    }

    

}