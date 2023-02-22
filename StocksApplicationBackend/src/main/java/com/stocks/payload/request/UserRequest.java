package com.stocks.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @Size(min = 4, max = 16, message = "Incorrect length")
    private String username;
    @Size(min = 4, max = 16, message = "Incorrect length")
    private String password;
}
