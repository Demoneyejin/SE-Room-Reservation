package com.reservo.reservo.Repository;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservo.reservo.DAL.User_DAL;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.Repository.UserRepository;
import com.reservo.reservo.Services.MongoUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;

@RestController
@CrossOrigin()
@RequestMapping(value = "/user")
public class UserController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    @Autowired
    private MongoUserDetailService userService;

    private BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final User_DAL userDal;

    public UserController(UserRepository userRepository, User_DAL userDal){
        this.userRepository = userRepository;
        this.userDal = userDal;
    }

    @RequestMapping(value = "/",  method = RequestMethod.GET)
    public List<User> getAlUsers(){
        LOG.info("Getting all users.");
        return userRepository.findAll();
    }

    /*
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Optional<User> getUser(@PathVariable String userId) {
        LOG.info("Getting user with ID: {}", userId);
        return userRepository.findById(userId);
    }*/

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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, String> login(@RequestBody Map<String, String> loginInfo){

        Map<String, String> returnMap = new HashMap<>();

        //if anything is empty, nada..
        if(loginInfo.isEmpty() || loginInfo.get("username").isEmpty() || loginInfo.get("password").isEmpty()){
            LOG.info("401"); //Error 204, cannot login.
            returnMap.put("error", "Missing information");
            return returnMap;
        }
        User user = userService.findUserTypeByUsername(loginInfo.get("username"));
        if(user == null){
            LOG.info("401"); //Error 204, cannot login.
            returnMap.put("error", "User not found");
            return returnMap;
        }
        else{
            if(BCryptPasswordEncoder.matches(loginInfo.get("password"), user.getPassword())){

                String base64_out = Base64.getEncoder().encodeToString((loginInfo.get("username") + ":" +loginInfo.get("password")).getBytes());

                returnMap.put("sessionkey", base64_out);
                returnMap.put("username", loginInfo.get("username"));
                return returnMap; //user found;
            }else{
                returnMap.put("error", "Incorrect Password");
                return returnMap;
            }
        }
        
    }

    /*
    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public Map<String, String> loginUser(@RequestBody Map<String, String> userInfo) {

        Map<String, String> returnMap = new HashMap<>();
        if (userInfo.isEmpty()){
            LOG.info("401");
            returnMap.put("error", "No info provided");
            return null;
        }

        String username = userInfo.get("username");
        String password = userInfo.get("password");

        User user  = userService.findUserTypeByUsername(username);

        if (user == null){

            LOG.debug("User is null");

            returnMap.put("error", "No user found");
            return returnMap;
        }
        else {
            LOG.debug("User is not null");
            if(BCrypt.checkpw(password, user.getPassword())){
                LOG.debug("Passwords match");
                returnMap.put("username", username);
                return returnMap;
            }
            else {
                returnMap.put("error", "Passwords do not match");
                return returnMap;
            }
        }

    }*/

    /*
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
    }*/
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Map<String, String> createNewUser(@RequestBody Map<String,String> newUser) throws JsonParseException, JsonMappingException, IOException {
        //create and populate the user..
        User user = new User();
        user.setUserName(newUser.get("username"));
        user.setFullName(newUser.get("name"));
        LOG.debug(newUser.get("password"));
        user.setUserPassword(newUser.get("password"));
        user.setUserEmail(newUser.get("email"));
        Map<String, String> securityQuestion = new HashMap<>();
        securityQuestion.put(newUser.get("question"), newUser.get("answer"));
        user.setSecurityScreening(securityQuestion);

        Map<String, String> returnMap = new HashMap<>();
        
        //check if the user exists..
        Boolean userExists = userService.findUserByUsername(user.getUserName());
        if (userExists) {
            returnMap.put("error", "User already exists");
            return returnMap;
        }
        else {
        userService.saveUser(user);
        LOG.info("successMessage", "User has been registered successfully");
        }
        returnMap.put("username", user.getUserName());
        String unamePw = user.getUserName() + ':' + user.getPassword();
        String base64 = Base64.getEncoder().encodeToString(unamePw.getBytes());
        returnMap.put("sessionkey", base64);
        return returnMap;
    }
    


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