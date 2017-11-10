package com.company.challenge.userapi.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.challenge.services.interfaces.ISrvAuth;
import com.company.challenge.userapi.inputs.Credentials;

@Controller
public class Auth {
	
	private static final Logger logger = LoggerFactory.getLogger(Auth.class);
	
	@Autowired
	ISrvAuth authService;
	
    @RequestMapping(path="/login", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE) 
    public @ResponseBody Object login(Credentials credentials) {
    	logger.info(String.format("/login reguest for email: %s", credentials.getUsername()));
    	Object message = authService.login(credentials);
        return message;
    }
}

