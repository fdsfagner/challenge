package com.fdsfagner.challenge.application.ports.out;

import com.fdsfagner.challenge.domain.Account;

public interface AccountPortOut {

    Account getAccountById(Long id);

    Account createAccount(Account acoAccount);
}
