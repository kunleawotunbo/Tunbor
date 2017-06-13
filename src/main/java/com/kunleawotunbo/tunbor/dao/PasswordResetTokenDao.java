/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.dao;

import com.kunleawotunbo.tunbor.model.PasswordResetToken;
import com.kunleawotunbo.tunbor.model.User;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface PasswordResetTokenDao {
    PasswordResetToken getPasswordResetToken(String token);
    User getUserByPasswordResetToken(String token);
}
