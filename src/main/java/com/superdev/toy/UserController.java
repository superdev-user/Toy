package com.superdev.toy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService svc;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id){
        User user = svc.findUserById(id);
        logger.info("get - : {}", user.toString());
        return user;
    }

    @PostMapping("/user")
    public User saveUser(User user){
        svc.save(user);
        logger.info("save - user : {}", user.toString());
        return user;
    }


}
