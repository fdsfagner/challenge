package com.fdsfagner.challenge.framework.adapters.out.h2;

import com.fdsfagner.challenge.application.ports.out.AccountPortOut;
import com.fdsfagner.challenge.domain.Account;
import com.fdsfagner.challenge.framework.adapters.out.h2.entity.AccountEntity;
import com.fdsfagner.challenge.framework.adapters.out.h2.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountAdapterOut implements AccountPortOut {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccountById(Long id) {
        Optional<AccountEntity> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return AccountMapper.mapAllToDomainEntity(account.get());
        }

        return null;
    }

    @Override
    public Account createAccount(Account account) {

        AccountEntity accountEntity = AccountMapper.mapAllToEntity(account);
        accountEntity = accountRepository.save(accountEntity);

        return AccountMapper.mapAllToDomainEntity(accountEntity);
    }
}
