package com.company.challenge.userapi.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.challenge.entities.User;
import com.company.challenge.services.interfaces.ISrvUser;

@Controller
public class Users {
	
	private static final Logger logger = LoggerFactory.getLogger(Users.class);
	
	@Autowired
	ISrvUser userService;
	
    @RequestMapping(path="/register", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE) 
    public @ResponseBody Object register(User user) {
    	logger.info(String.format("/registering reguest with email: %s", user.getEmail()));
    	Object message = userService.register(user);
        return message;
    }
}

