package com.reservo.reservo.Repository;

import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.Services.MongoUserDetailService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{
    @Autowired
    private MongoUserDetailService userService;
    @Autowired
    private BCryptPasswordEncoder BCryptPasswordEncoder;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String Username, String Password){
        //if anything is empty, nada..
        if(Username.isEmpty() || Password.isEmpty()){
            LOG.info("401"); //Error 204, cannot login. 
            return "Missing information";
        }
        User user = userService.findUserByUsername(Username);
        if(user == null){
            LOG.info("401"); //Error 204, cannot login. 
            return "User not found.";
        }
        else{
            if(BCryptPasswordEncoder.matches(Password, user.getPassword())){
                return "200"; //user found;
            }else{
                return "Wrong Password";
            }
        }
        
    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUsername(user.getUserName());
        if (userExists != null) {
            bindingResult.rejectValue("email", "error.user", "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        }
        else {
        userService.saveUser(user);
        modelAndView.addObject("successMessage", "User has been registered successfully");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("login");

        }
        return modelAndView;
    }
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUsername(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getUserName());
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }


}