package com.kunleawotunbo.tunbor.controller;

import com.kunleawotunbo.tunbor.bean.UserBean;
import com.kunleawotunbo.tunbor.model.User;
import com.kunleawotunbo.tunbor.model.UserProfile;
import com.kunleawotunbo.tunbor.service.MailService;
import com.kunleawotunbo.tunbor.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RestController
//@RequestMapping("/")
//@SessionAttributes("roles")
@Api(value = "users", description = "Endpoint for user management")
public class AppController {

    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
    @Autowired
    MailService mailService;
    
    static final Logger logger = LoggerFactory.getLogger(AppController.class);

    /*
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String userBean(ModelMap model) {
        System.out.println("Inside here");
        UserBean userBean = new UserBean();
        userBean.setFirstName("Olakunle");
        userBean.setLastName("Awotunbo");
        model.addAttribute("users", userBean);
        model.addAttribute("loggedinuser", getPrincipal());
        return "userslist";
    }
    
    */

    	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
             System.out.println("Inside listUsers");
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("loggedinuser", getPrincipal());
		return "userslist";
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
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        //model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createUser(@RequestBody User user) {
        boolean success = false;
        userService.saveUser(user);
        logger.info("Inside createUser");

        mailService.sendMail(user);

        return "User successfully created";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    
  
}
