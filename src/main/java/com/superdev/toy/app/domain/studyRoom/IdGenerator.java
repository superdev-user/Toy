package com.superdev.toy.app.domain.studyRoom;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class IdGenerator {

    private Random random;

    public IdGenerator() {
        random = new Random(System.currentTimeMillis());
    }

    private String uniqueId() {
        long uid = Instant.now().toEpochMilli() * 1000 + random.nextInt(1000);
        return Long.toHexString(uid);
    }

    public String next() {
        return uniqueId();
    }
}
