package com.fdsfagner.challenge.application.ports.in;

import com.fdsfagner.challenge.application.ports.out.TransactionPortOut;
import com.fdsfagner.challenge.application.queries.TransationQuery;
import com.fdsfagner.challenge.domain.Account;
import com.fdsfagner.challenge.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@Transactional
public class TransationPortIn implements TransationQuery {

    @Autowired
    private TransactionPortOut transactionPortOut;

    @Override
    public Transaction deposit(Account destination, BigDecimal amount) {
        return transactionPortOut.deposit(destination, amount);
    }

    @Override
    public Transaction withdraw(Account origin, BigDecimal amount) {
        return transactionPortOut.withdraw(origin, amount);
    }

    @Override
    public Transaction transfer(Account origin, Account destination, BigDecimal amount) {
        return transactionPortOut.transfer(origin, destination, amount);
    }

    @Override
    public void reset() {
        transactionPortOut.reset();
    }
}
