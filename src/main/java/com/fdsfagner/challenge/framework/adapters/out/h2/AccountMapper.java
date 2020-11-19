package com.fdsfagner.challenge.framework.adapters.out.h2;

import com.fdsfagner.challenge.domain.Account;
import com.fdsfagner.challenge.framework.adapters.out.h2.entity.AccountEntity;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountEntity mapAllToEntity(Account account) {
        return new AccountEntity(
                account.getId(), account.getBalance().setScale(0));
    }

    public static Account mapAllToDomainEntity(
            AccountEntity account) {
        return new Account(
                account.getId(),
                account.getBalance().setScale(0));
    }

}
