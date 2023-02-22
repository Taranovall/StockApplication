package com.stocks.service.impl;

import com.stocks.entity.Account;
import com.stocks.repository.AccountRepository;
import com.stocks.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void updateAccount(Account account) {
        account.getStocks().entrySet().removeIf(e -> e.getValue() == 0);
        accountRepository.save(account);
    }
}
