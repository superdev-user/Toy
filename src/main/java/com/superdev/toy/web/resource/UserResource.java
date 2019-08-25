package com.superdev.toy.web.resource;


import java.nio.file.AccessDeniedException;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.service.UserService;
import com.superdev.toy.web.common.jwt.JwtTokenProvider;
import com.superdev.toy.web.domain.UserRequest;
import com.superdev.toy.web.domain.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @CrossOrigin("*")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> join(@RequestBody UserRequest userReq){

        User user = userService.saveUser(userReq);

        String token = jwtTokenProvider.createToken(user.getUsername(), this.userService.getUserName(user.getUserNm()).getRoles());
        return ok(new UserResponse(user.getUsername(), token));
    }


    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin(@RequestBody UserRequest userReq) {

        User user = this.userService.getUserName(userReq.getUserNm());
        if(user == null) new AccessDeniedException("User Not Found");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserNm(), userReq.getUserPwd()));

        String token = jwtTokenProvider.createToken(userReq.getUserNm(), user.getRoles());
        return ok(new UserResponse(userReq.getUserNm(), token));
    }

}
