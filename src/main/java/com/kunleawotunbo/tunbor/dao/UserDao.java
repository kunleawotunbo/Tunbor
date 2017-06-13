/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.dao;

import com.kunleawotunbo.tunbor.model.PasswordResetToken;
import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.model.VerificationToken;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * @author olakunle
 */
public interface UserDao {
 
    User findById(int id);
     
    User findByUsername(String username);
     
    boolean save(User user);
     
    void deleteByUsername(String sso);
     
    List<User> findAllUsers();
    
    public boolean isUserExist(User user);
    
    void updateUser(User user);
     
    void deleteUserByUsername(String username);
     
    boolean isUserUsernameUnique(Integer id, String username);
    
    User getUser(String verificationToken);  
    


    User getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();
 
}
