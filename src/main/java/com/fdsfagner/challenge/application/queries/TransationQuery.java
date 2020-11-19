package com.fdsfagner.challenge.application.queries;

import com.fdsfagner.challenge.domain.Account;
import com.fdsfagner.challenge.domain.Transaction;

import java.math.BigDecimal;

public interface TransationQuery {

    Transaction deposit(Account destination, BigDecimal amount);

    Transaction withdraw(Account origin, BigDecimal amount);

    Transaction transfer(Account origin, Account destination, BigDecimal amount);

    void reset();
}
