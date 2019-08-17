package com.superdev.toy;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.superdev.toy.web.common.jwt.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ToyApplicationTests {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @Ignore
    public void contextLoads() {

        String token = jwtTokenProvider.createToken("user", Arrays.asList("user"));

//        log.info("Token : {}", token);
        Assert.assertNotNull(token);
    }

}
