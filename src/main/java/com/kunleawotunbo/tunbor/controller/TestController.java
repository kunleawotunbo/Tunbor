/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.controller;

import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.service.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author olakunle
 */
@Controller
@RequestMapping("test")
@Api(value = "custom", description = "Endpoint for custom management")
public class TestController {

    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
    static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public String custom() {
        return "custom";
    }

    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        logger.info("Inside listAllUsers()");
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
