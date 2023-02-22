package com.stocks.controller;

import com.stocks.entity.User;
import com.stocks.exception.IncorrectInputException;
import com.stocks.exception.IncorrectPasswordException;
import com.stocks.payload.request.UserRequest;
import com.stocks.payload.response.JwtResponse;
import com.stocks.payload.response.UserResponse;
import com.stocks.service.UserService;
import com.stocks.util.ConverterUtil;
import com.stocks.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final ConverterUtil converter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign_up")
    public ResponseEntity<JwtResponse> signUp(@RequestBody @Valid UserRequest userRequest, BindingResult result) {
        handleBindingResult(result);

        User user = converter.convertUserDtoToEntity(userRequest);
        userService.createUser(user);

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(
                token,
                new UserResponse(user.getId(), user.getUsername(), user.getRole(), user.getAccount())
        ));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<JwtResponse> signIn(@RequestBody @Valid UserRequest userRequest, BindingResult result) {
        handleBindingResult(result);
        User user = userService.findByUsername(userRequest.getUsername());

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password");
        }

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(
                token,
                new UserResponse(user.getId(), user.getUsername(), user.getRole(), user.getAccount())
        ));
    }

    private void handleBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            String message = String.format("%s of %s",
                    result.getFieldError().getDefaultMessage(),
                    result.getFieldError().getField());
            throw new IncorrectInputException(message);
        }
    }
}
