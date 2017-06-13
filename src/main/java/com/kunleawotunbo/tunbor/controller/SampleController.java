/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.controller;

import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.service.MailService;
import com.kunleawotunbo.tunbor.service.UserService;
import com.kunleawotunbo.tunbor.service.VerificationTokenService;
import com.kunleawotunbo.tunbor.utility.TunborUtility;
import io.swagger.annotations.Api;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author olakunle
 */
@Controller
@RequestMapping("/test/")
@Api(value = "sample", description = "Endpoint for sample")
public class SampleController {

    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
    @Autowired
    MailService mailService;
    @Autowired
    TunborUtility tunborUtility;
    @Autowired
    VerificationTokenService verificationTokenService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /*
        {
            "firstName": "Olakunle",
            "lastName": "Awotunbo",
            "phoneNumber": "07031528126",
            "email": "kunleawotunbo@gmail.com",
            "password": "1@Yemisi"
        }
     */
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public String custom() {
        return "custom";
    }

    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        //List<User> users = userService.findAllUsers();
        List<User> users = null;
        User user = new User();
        user.setFirstName("Olakunle");
        user.setLastName("Awotunbo");
        //users.add(user);
        logger.info("Inside listAllUsers()");
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "/listuser/", method = RequestMethod.GET)
    public ResponseEntity<User> list() {
        //List<User> users = userService.findAllUsers();
        List<User> users = null;
        User user = new User();
        user.setFirstName("Olakunle");
        user.setLastName("Awotunbo");
        //users.add(user);

        logger.info("Inside users()");
        /*
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        
         */
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //-------------------Create a User--------------------------------------------------------
    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder, HttpServletRequest request) {
        System.out.println("Creating User " + user.getFirstName());
        boolean created = false;
        String appUrl = "";

        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getEmail() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        user.setUserName(user.getEmail());
        created = userService.saveUser(user);
        logger.info("About to send mail to ::" + user.getEmail());
        if(created){
            appUrl = request.getContextPath();
            
            try {
                final String token = UUID.randomUUID().toString();
                verificationTokenService.createVerificationTokenForUser(user, token);
                
                final String confirmationUrl = getURLBase(request) + "/registrationConfirm.html?token=" + token;
                user.setUserName(confirmationUrl);
                tunborUtility.sendMail(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("User not created ");
        }
        

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    public String getURLBase(HttpServletRequest request) throws MalformedURLException {

    URL requestURL = new URL(request.getRequestURL().toString());
    String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
    //return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    return requestURL.getProtocol() + "://" + requestURL.getHost() + port +  request.getContextPath();

}
}
