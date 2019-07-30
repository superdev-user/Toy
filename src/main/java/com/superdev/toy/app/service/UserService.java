package com.superdev.toy.app.service;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.repo.UserRepository;
import com.superdev.toy.web.domain.UserRequest;
import com.superdev.toy.web.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User saveUser(UserRequest userRequest){

        User user = userMapper.map(userRequest);
        return userRepository.save(user);
    }

}
