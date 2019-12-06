package com.reservo.reservo.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


import com.reservo.reservo.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MongoUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
   // @Autowired
    //private RoleRepository RoleRepository;
    
    private BCryptPasswordEncoder BCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        com.reservo.reservo.Models.User user = userRepository.findByUserName(userId);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
        return new User(user.getUserName(), user.getPassword(), authorities);
    }
    //find user by username but should also work for email.
    public Boolean findUserByUsername(String username){
        return userRepository.existsByUserName(username);
    }

    public com.reservo.reservo.Models.User findUserTypeByUsername(String username){
        return userRepository.findByUserName(username); 
    }

    public void saveUser(com.reservo.reservo.Models.User user){
        user.setUserPassword(BCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private List<GrantedAuthority> getUserAuthority(Set<String> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        //userRoles.forEach((role) -> {roles.add(new SimpleGrantedAuthority(role.getRole()));});

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    
}