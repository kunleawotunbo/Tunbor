/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.service;

import com.kunleawotunbo.tunbor.dao.UserDao;
import com.kunleawotunbo.tunbor.model.User;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author olakunle
 */
@Service("userService")
//@Component
@Transactional
public class UserServiceImpl implements UserService{
 
    @Autowired
    private UserDao userDao;
 
   @Autowired
   private PasswordEncoder passwordEncoder;
     
    public User findById(int id) {
        return userDao.findById(id);
    }
 
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        return user;
    }
 
    public boolean saveUser(User user) {
        return userDao.save(user);
    }
 
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(User user) {
        /*
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
        */
        userDao.updateUser(user);
    }
 
     
    public void deleteUserByUsername(String username) {
        userDao.deleteByUsername(username);
    }
 
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }
 
    public boolean isUserUsernameUnique(Integer id, String username) {
      //  User user = findByUsername(username);
      //  return ( user == null || ((id != null) && (user.getId() == id)));
      return userDao.isUserUsernameUnique(id, username);
    }

    public boolean isUserExist(User user) {
        return userDao.isUserExist(user);
    }

    public User getUser(String verificationToken) {
        return userDao.getUser(verificationToken);
    }

    public User getUserByID(long id) {
        return userDao.getUserByID(id);
    }

    public void changeUserPassword(User user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean checkIfValidOldPassword(User user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String validateVerificationToken(String token) {
        return userDao.validateVerificationToken(token);
    }

    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public User updateUser2FA(boolean use2FA) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getUsersFromSessionRegistry() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}