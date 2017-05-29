package com.kunleawotunbo.tunbor.controller;

import com.kunleawotunbo.tunbor.bean.UserBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@RestController
//@RequestMapping("/")
//@SessionAttributes("roles")
@Api(value = "users", description = "Endpoint for user management")
public class AppController {

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String userBean(ModelMap model) {
        System.out.println("Inside here");
         UserBean userBean = new UserBean();
        userBean.setFirstName("Olakunle");
        userBean.setLastName("Awotunbo");
        model.addAttribute("users", userBean);
        //model.addAttribute("loggedinuser", getPrincipal());
        return "userslist";
    }

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(ModelMap model) {
        System.out.println("Inside login");
        return "login";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        System.out.println("I am inside test()");

        return "login";
    }

    //-------------------Retrieve All Users--------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String listAllUsers() {
        System.out.println("I am inside listAllUsers()");

        return "ok";
    }
    
      //-------------------Retrieve Single User--------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBean> getUser(@ApiParam(value = "testId",
	        required = true) @PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        UserBean user = new UserBean();
        user.setFirstName("Olakunle");
        user.setLastName("Awotunbo");
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<UserBean>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserBean>(user, HttpStatus.OK);
    
    }

}
