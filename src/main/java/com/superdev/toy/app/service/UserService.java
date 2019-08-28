package com.superdev.toy.app.service;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.repo.UserRepository;
import com.superdev.toy.web.domain.UserRequest;
import com.superdev.toy.web.domain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userService")
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserRequest userRequest){

        User user = userMapper.map(userRequest);
        user.setUserPwd(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

    public User getUserName(String userId){ return this.userRepository.findByUserNm(userId).get();}
    public User getUserId(String userId) { return this.userRepository.findByUserId(userId).get();}
}
