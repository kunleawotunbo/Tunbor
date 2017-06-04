/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.service;

import com.kunleawotunbo.tunbor.dao.UserDao;
import com.kunleawotunbo.tunbor.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author olakunle
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserDao dao;
 
   @Autowired
   private PasswordEncoder passwordEncoder;
     
    public User findById(int id) {
        return dao.findById(id);
    }
 
    public User findByUsername(String username) {
        User user = dao.findByUsername(username);
        return user;
    }
 
    public void saveUser(User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       dao.save(user);
    }
 
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setUserName(user.getUserName());
            if(!user.getPassword().equals(entity.getPassword())){
               entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setUserProfile(user.getUserProfile());
        }
    }
 
     
    public void deleteUserByUsername(String username) {
        dao.deleteByUsername(username);
    }
 
    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }
 
    public boolean isUserUsernameUnique(Integer id, String username) {
        User user = findByUsername(username);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }
     
}