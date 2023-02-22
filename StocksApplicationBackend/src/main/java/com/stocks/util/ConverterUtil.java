package com.stocks.util;

import com.stocks.entity.User;
import com.stocks.payload.request.UserRequest;
import com.stocks.payload.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConverterUtil {

    private final ModelMapper mapper;

    public User convertUserDtoToEntity(UserRequest userRequest) {
        return mapper.map(userRequest, User.class);
    }

    public UserResponse convertUsertoUserResponse(User user) {
        return mapper.map(user, UserResponse.class);
    }
}
