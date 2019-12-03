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
   //Being moved to user controller, check there for now.

}