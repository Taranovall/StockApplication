package com.stocks.payload.response;

import com.stocks.entity.Account;
import com.stocks.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    private Long id;
    private String username;
    private Role role;

    private Account account;
}
