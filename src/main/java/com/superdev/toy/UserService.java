package com.superdev.toy;

public interface UserService {
    User findUserById (String id);
    void save(User user);
}