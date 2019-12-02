package com.reservo.reservo.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.reservo.reservo.Models.Role;
import com.reservo.reservo.Repository.RoleRepository;
import com.reservo.reservo.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RoleRepository RoleRepository;
    @Autowired
    private BCryptPasswordEncoder BCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<com.reservo.reservo.Models.User> user = userRepository.findById(userId);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(user.get().getUserName(), user.get().getPassword(), authorities);
    }
    //find user by username but should also work for email.
    public com.reservo.reservo.Models.User findUserByUsername(String username){
        return userRepository.findById(username).get();
    }

    public void saveUser(com.reservo.reservo.Models.User user){
        user.setUserPassword(BCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = RoleRepository.findbyRole("User");
        user.appendUserSettings(userRole.getID(), userRole.getRole()); //assigns the role to the user setting
        userRepository.save(user);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {roles.add(new SimpleGrantedAuthority(role.getRole()));});

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    
}