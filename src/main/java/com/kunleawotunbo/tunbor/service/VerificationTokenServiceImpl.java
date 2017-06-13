/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.service;

import com.kunleawotunbo.tunbor.dao.VerificationTokenDao;
import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.model.VerificationToken;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("verificationTokenService")
//@Component
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenDao verificationTokenDao;

    public VerificationToken findByToken(String token) {
        return verificationTokenDao.findByToken(token);
    }

    public VerificationToken findByUser(User user) {
        return verificationTokenDao.findByUser(user);
    }

    public Stream<VerificationToken> findAllByExpiryDateLessThan(Date now) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteByExpiryDateLessThan(Date now) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteAllExpiredSince(Date now) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void createVerificationTokenForUser(User user, String token) {
         verificationTokenDao.createVerificationTokenForUser(user, token);
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenDao.getVerificationToken(VerificationToken);
    }

    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        return verificationTokenDao.generateNewVerificationToken(existingVerificationToken);
    }

    public boolean deleteVerificationToken(VerificationToken verificationToken) {
        return verificationTokenDao.deleteVerificationToken(verificationToken);
    }

}
