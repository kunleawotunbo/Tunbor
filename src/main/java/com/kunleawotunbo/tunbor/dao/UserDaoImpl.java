/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kunleawotunbo.tunbor.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author olakunle
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    @Autowired
    private PasswordEncoder passwordEncoder;
   // static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isUserUsernameUnique(Integer id, String username) {
       User user = findByUsername(username);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }

}
