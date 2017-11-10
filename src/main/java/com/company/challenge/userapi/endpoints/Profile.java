package com.company.challenge.userapi.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.challenge.services.interfaces.ISrvUser;

@Controller
public class Profile {
	
	private static final Logger logger = LoggerFactory.getLogger(Profile.class);
	
	@Autowired
	ISrvUser userService;
	
    @RequestMapping(path="/profile/{uuid}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) 
    public @ResponseBody Object profile(@PathVariable("uuid") String uuid) {
    	logger.info(String.format("/profile reguest with uuidl: %s", uuid));
    	String token = ""; // get from header
    	Object message = userService.profile(uuid, token);
    	return message;
    }
}

