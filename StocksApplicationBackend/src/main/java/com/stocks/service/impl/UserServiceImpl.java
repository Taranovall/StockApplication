package com.stocks.service.impl;

import com.stocks.entity.Account;
import com.stocks.entity.Role;
import com.stocks.entity.User;
import com.stocks.exception.UserAlreadyExistsException;
import com.stocks.exception.UserNotFoundException;
import com.stocks.repository.UserRepository;
import com.stocks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${user.default.amount-of-money}")
    private Integer defaultAmountOfMoney;

    @Override
    public User createUser(User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        if (foundUser.isPresent()) {
            String message = String.format("Username '%s' already exists", user.getUsername());
            throw new UserAlreadyExistsException(message);
        }

        user.setRole(Role.USER)
                .setPassword(passwordEncoder.encode(user.getPassword()))
                .setAccount(new Account()
                        .setStocks(new HashMap<>())
                        .setAmountOfMoney(defaultAmountOfMoney)
                );
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.orElseThrow(() -> {
            String message = String.format("User with username '%s' not found", username);
            throw new UserNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> {
            String message = String.format("User with id '%d' not found", id);
            throw new UserNotFoundException(message);
        });
    }
}
