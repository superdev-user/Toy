package com.superdev.toy.web.resource;


import java.nio.file.AccessDeniedException;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.service.UserService;
import com.superdev.toy.web.common.jwt.JwtTokenProvider;
import com.superdev.toy.web.domain.SigninRequest;
import com.superdev.toy.web.domain.SigninResponse;
import com.superdev.toy.web.domain.UserRequest;
import com.superdev.toy.web.domain.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SigninResponse> join(@RequestBody UserRequest userReq){
        User user = userService.saveUser(userReq);
        return ok(new SigninResponse(user.getUserId(), HttpStatus.OK.value()));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin(@RequestBody SigninRequest signupReq) {

        UserResponse response = null;
        try {
            User user = this.userService.getUserId(signupReq.getUserId());
            if (user == null) new AccessDeniedException("User Not Found");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), signupReq.getUserPwd()));
            String token = "Bearer "+jwtTokenProvider.createToken(signupReq.getUserId(), user.getRoles());
            response = new UserResponse(signupReq.getUserId(), token, HttpStatus.OK.value());

        } catch (Exception err) {
            System.out.println(err.toString());
            response = new UserResponse( HttpStatus.NOT_FOUND.value());
        }finally {
            return ok(response);
        }
    }
}
