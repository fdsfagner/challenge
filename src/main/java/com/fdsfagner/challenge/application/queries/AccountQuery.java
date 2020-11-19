package com.fdsfagner.challenge.application.queries;

import com.fdsfagner.challenge.domain.Account;

public interface AccountQuery {

    Account getAccountById(Long id);

    Account createAccount(Account account);
}
