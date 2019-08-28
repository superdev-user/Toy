package com.superdev.toy.web.resource;


import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.service.UserService;
import com.superdev.toy.web.common.jwt.JwtTokenProvider;
import com.superdev.toy.web.domain.UserRequest;
import com.superdev.toy.web.domain.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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

    @CrossOrigin("*")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> join(@RequestBody UserRequest userReq){

        User user = userService.saveUser(userReq);

        //todo 질문 , 회원가입시 token을 돌려주는 이유.
        String token = jwtTokenProvider.createToken(user.getUserId(), this.userService.getUserId(user.getUserId()).getRoles());

        return ok(new UserResponse(user.getUserId(), token , HttpStatus.OK.value()));
    }

    @CrossOrigin("*")
    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin(@RequestBody UserRequest userReq) {

        //User user = this.userService.getUserName(userReq.getUserNm());
        UserResponse response = null;
        try {
            User user = this.userService.getUserId(userReq.getUserId());
            if (user == null) new AccessDeniedException("User Not Found");
            System.out.println(user);

            // todo getUserNm으로 해야지만 돌아가는 이유 .
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), userReq.getUserPwd()));

            String token = jwtTokenProvider.createToken(userReq.getUserId(), user.getRoles());
            System.out.println("signinToken--");
            System.out.println(token);
            System.out.println("signinToken--");
            response = new UserResponse(userReq.getUserId(), token, HttpStatus.OK.value());

        } catch (Exception err) {
            System.out.println(err.toString());
            response = new UserResponse( HttpStatus.NOT_FOUND.value());
        }finally {
            return ok(response);
        }
    }
}
