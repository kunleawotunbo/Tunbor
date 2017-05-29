/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.dao;

import com.kunleawotunbo.tunbor.model.User;
import java.util.List;

/**
 *
 * @author olakunle
 */
public interface UserDao {
 
    User findById(int id);
     
    User findByUsername(String username);
     
    void save(User user);
     
    void deleteByUsername(String sso);
     
    List<User> findAllUsers();
 
}
