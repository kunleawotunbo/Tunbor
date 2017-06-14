/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.security;

import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.model.UserProfile;
import com.kunleawotunbo.tunbor.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author olakunle
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
    
    static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {        
        User user = userService.findByUsername(userName);
        logger.info("User : {}", user);
        if (user == null){
            logger.info("User not found");
           throw new UsernameNotFoundException("No user found with username: " + userName);
        }
        /*
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
        */
      
         return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.isEnabled(), true, true, true, getGrantedAuthorities(user));
       
    }
    
    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for (UserProfile userProfile : user.getUserProfile()){
            logger.info("UserProfile : {}", userProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));
        }
        logger.info("authorities : {}", authorities);
        return authorities;
    }
    
}
