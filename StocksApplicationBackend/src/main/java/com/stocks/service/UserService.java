package com.stocks.service;

import com.stocks.entity.User;

public interface UserService {

    User createUser(User user);

    User findByUsername(String username);

    User findById(Long id);
}
