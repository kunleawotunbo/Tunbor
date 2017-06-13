/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.dao;

import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.model.VerificationToken;
import java.util.Date;
import java.util.stream.Stream;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface VerificationTokenDao {
    
      VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    //@Modifying
   // @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
    
     void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String existingVerificationToken);
    
    String validateVerificationToken(String token);
    
    boolean deleteVerificationToken(VerificationToken verificationToken);

}
