package com.superdev.toy.web.resource;


import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.service.UserService;
import com.superdev.toy.web.domain.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @PostMapping
    public User test(UserRequest userRequest){
        return userService.saveUser(userRequest);
    }

}
