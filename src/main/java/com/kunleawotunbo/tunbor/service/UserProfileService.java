/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.service;

import com.kunleawotunbo.tunbor.model.UserProfile;
import java.util.List;

/**
 *
 * @author olakunle
 */
public interface UserProfileService {
 
    UserProfile findById(int id);
 
    UserProfile findByType(String type);
     
    List<UserProfile> findAll();
     
}
