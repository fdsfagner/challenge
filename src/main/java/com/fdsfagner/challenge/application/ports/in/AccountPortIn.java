package com.fdsfagner.challenge.application.ports.in;

import com.fdsfagner.challenge.application.ports.out.AccountPortOut;
import com.fdsfagner.challenge.application.queries.AccountQuery;
import com.fdsfagner.challenge.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AccountPortIn implements AccountQuery {

    @Autowired
    private AccountPortOut accountPortOut;

    @Override
    public Account getAccountById(Long id) {
        return accountPortOut.getAccountById(id);
    }

    @Override
    public Account createAccount(Account account) {
        return accountPortOut.createAccount(account);
    }
}
