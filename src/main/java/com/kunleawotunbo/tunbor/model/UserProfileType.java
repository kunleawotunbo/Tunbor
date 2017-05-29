/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.model;

import java.io.Serializable;

/**
 *
 * @author olakunle
 */
public enum UserProfileType implements Serializable{
    USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");
     
    String userProfileType;
     
    private UserProfileType(String userProfileType){
        this.userProfileType = userProfileType;
    }
     
    public String getUserProfileType(){
        return userProfileType;
    }
     
}
