package com.company.challenge.userapi.endpoints;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hello {

    @RequestMapping(path="/hello", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) 
    public @ResponseBody String hello() {
        return "{\"message\":\"hello\"}";
    }

}