/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.dao;

import com.kunleawotunbo.tunbor.model.PasswordResetToken;
import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.model.VerificationToken;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Olakunle Awotunbo
 */
@Repository("verificationTokenDao")
public class VerificationTokenDaoImpl extends AbstractDao<Long, VerificationToken> implements VerificationTokenDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public VerificationToken findByToken(String token) {
        logger.info("token : {}", token);

        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("token", token));
        return (VerificationToken) crit.uniqueResult();
    }

    public VerificationToken findByUser(User user) {
        logger.info("user id : {}", user.getId());
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("user_id", user.getId()));
        return (VerificationToken) crit.uniqueResult();
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
        logger.info("user id : {}", user.getId());
        logger.info("token: {}", token);

        final VerificationToken myToken = new VerificationToken(token, user);
        persist(myToken);
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return findByToken(VerificationToken);
    }

    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        //   vToken = tokenRepository.save(vToken);
        update(vToken);
        return vToken;
    }

    public String validateVerificationToken(String token) {
        return "";
    }

    public boolean deleteVerificationToken(VerificationToken verificationToken) {
        boolean success = false;
        try {

            persist(verificationToken);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

}
