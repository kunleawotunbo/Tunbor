/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.dao;

import com.kunleawotunbo.tunbor.model.PasswordResetToken;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.model.VerificationToken;
import com.kunleawotunbo.tunbor.service.VerificationTokenService;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author olakunle
 */
//@Component
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public User findById(int id) {
        User user = getByKey(id);
        if (user != null) {
            Hibernate.initialize(user.getUserProfile());
        }
        return user;
    }

    public User findByUsername(String username) {
        logger.info("username : {}", username);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("userName", username));
        User user = (User) crit.uniqueResult();
        if (user != null) {
            Hibernate.initialize(user.getUserProfile());
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<User> users = (List<User>) criteria.list();

        // No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
        /*
        for(User user : users){
            Hibernate.initialize(user.getUserProfiles());
        }*/
        return users;
    }

    public boolean save(User user) {
        boolean success = false;
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            persist(user);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public void deleteByUsername(String username) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("userName", username));
        User user = (User) crit.uniqueResult();
        delete(user);
    }

    public boolean isUserExist(User dUser) {
        logger.info("checking is user exist : " + dUser.getEmail());
        System.out.println("checking is user exist : " + dUser.getEmail());
        // return findByUsername(dUser.getEmail())!=null; 
        User user = findByUsername(dUser.getEmail());
        if (user != null) {
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        User entity = findById(user.getId());
        if (entity != null) {
            entity.setUserName(user.getUserName());
            if (!user.getPassword().equals(entity.getPassword())) {
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setUserProfile(user.getUserProfile());
        }
    }

    public void deleteUserByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isUserUsernameUnique(Integer id, String username) {
        User user = findByUsername(username);
        return (user == null || ((id != null) && (user.getId() == id)));
    }

    public User getUser(String verificationToken) {
        final VerificationToken token = verificationTokenService.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    public User getUserByID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void changeUserPassword(User user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean checkIfValidOldPassword(User user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            verificationTokenService.deleteVerificationToken(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        update(user);
        return TOKEN_VALID;
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
