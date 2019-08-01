package com.superdev.toy.app.service;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.repo.UserRepository;
import com.superdev.toy.web.domain.UserRequest;
import com.superdev.toy.web.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userService")
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User saveUser(UserRequest userRequest){

        User user = userMapper.map(userRequest);
        return userRepository.save(user);
    }

    public UserService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUserNm(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }
}
