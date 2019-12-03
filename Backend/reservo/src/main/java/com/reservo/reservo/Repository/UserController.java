package com.reservo.reservo.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jdk.internal.org.jline.utils.Log;

import com.reservo.reservo.DAL.User_DAL;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.Repository.UserRepository;
import com.reservo.reservo.Services.MongoUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    @Autowired
    private MongoUserDetailService userService;
    @Autowired
    private BCryptPasswordEncoder BCryptPasswordEncoder;

    private final User_DAL userDal;

    public UserController(UserRepository userRepository, User_DAL userDal){
        this.userRepository = userRepository;
        this.userDal = userDal;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUser(@RequestBody User user){
        LOG.info("Saving user.");
        return userRepository.save(user);
    }

    @RequestMapping(value = "",  method = RequestMethod.GET)
    public List<User> getAlUsers(){
        LOG.info("Getting all users.");
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Optional<User> getUser(@PathVariable String userId) {
        LOG.info("Getting user with ID: {}", userId);
        return userRepository.findById(userId);
    }

    @RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
    public String getUserSettings(@PathVariable String userId, @PathVariable String key){
        return userDal.getUserSettings(userId, key);
    }

    @RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
    public String addUserSettings(@PathVariable String userId, @PathVariable String key, @PathVariable String value){
        Optional<User> user = userRepository.findById(userId);
        if(user != null){
            User localUser = user.get();
            localUser.getUserSettings().put(key, value);
            userRepository.save(localUser);
            return "Key added";
        }else{
            return "User not found.";
        }
    }

    @RequestMapping(value = "/login/{userId}/{password}", method = RequestMethod.POST)
    public String login(String userId, String password){
        //if anything is empty, nada..
        if(userId.isEmpty() || password.isEmpty()){
            LOG.info("401"); //Error 204, cannot login. 
            return "Missing information";
        }
        User user = userService.findUserByUsername(userId);
        if(user == null){
            LOG.info("401"); //Error 204, cannot login. 
            return "User not found.";
        }
        else{
            if(BCryptPasswordEncoder.matches(password, user.getPassword())){
                return "200"; //user found;
            }else{
                return "Wrong Password";
            }
        }
        
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User createNewUser( Map<String,String> newUser, BindingResult bindingResult) {
        //create and populate the user..
        User user = new User();
        user.setUserName(newUser.get("userName"));
        user.setFullName(newUser.get("fullName"));
        user.setUserPassword(newUser.get("password"));
        user.setUserEmail(newUser.get("email"));
        Map<String, String> securityQuestion = new HashMap<>();
        securityQuestion.put(newUser.get("securityQuestion"), newUser.get("securityAnswer"));
        user.setSecurityScreening(securityQuestion);
        //check if the user exists..
        User userExists = userService.findUserByUsername(user.getUserName());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            return null;
        }
        else {
        userService.saveUser(user);
        LOG.info("successMessage", "User has been registered successfully");
        }
        return user;
    }
    /*
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String createNewUser( Map<String,String> newUser, BindingResult bindingResult) {
        //create and populate the user..
        User user = new User();
        user.setUserName(newUser.get("userName"));
        user.setUserPassword(newUser.get("password"));
        user.setUserEmail(newUser.get("email"));
        user.setFirstName(newUser.get("firstName"));
        user.setLastName(newUser.get("lastName"));
        Map<String, String> securityQuestion = new HashMap<>();
        securityQuestion.put(newUser.get("question"), newUser.get("answer"));
        user.setSecurityScreening(securityQuestion);
        //check if the user exists..
        User userExists = userService.findUserByUsername(user.getUserName());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            return "403 FORBIDDEN";
        }
        else {
        userService.saveUser(user);
        LOG.info("successMessage", "User has been registered successfully");
        }
        return "200 OK";
    }
    */


    /* traditional implementation, trying out the DAL implementation (utilizing mongos interface options)/(Above this)
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
    }*/

    

}