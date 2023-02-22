package com.stocks.controller;

import com.stocks.entity.User;
import com.stocks.exception.IncorrectInputException;
import com.stocks.payload.response.UserResponse;
import com.stocks.service.UserService;
import com.stocks.util.Constant;
import com.stocks.util.ConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConverterUtil converterUtil;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        if (id < 0)
            throw new IncorrectInputException(Constant.USER_ID_ERROR);

        User user = userService.findById(id);

        return ResponseEntity.ok(converterUtil.convertUsertoUserResponse(user));
    }
}
