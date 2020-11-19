package com.fdsfagner.challenge.framework.adapters.out.h2;

import com.fdsfagner.challenge.domain.Transaction;
import com.fdsfagner.challenge.framework.adapters.out.h2.entity.TransactionEntity;

public class TransactionMapper {

    private TransactionMapper() {}

    public static Transaction mapAllToDomainEntity(
            TransactionEntity transactionEntity) {

        Transaction transaction = new Transaction();
        transaction.setId(transactionEntity.getId());
        transaction.setAmount(transactionEntity.getAmount());

        if (transactionEntity.getOrigin() != null) {
            transaction.setOrigin(AccountMapper.mapAllToDomainEntity(transactionEntity.getOrigin()));
        }

        if (transactionEntity.getDestination() != null) {
            transaction.setDestination(AccountMapper.mapAllToDomainEntity(transactionEntity.getDestination()));
        }

        return transaction;
    }

}
