package com.fdsfagner.challenge.framework.adapters.out.h2;

import com.fdsfagner.challenge.application.ports.out.TransactionPortOut;
import com.fdsfagner.challenge.domain.Account;
import com.fdsfagner.challenge.domain.Transaction;
import com.fdsfagner.challenge.framework.adapters.out.h2.entity.AccountEntity;
import com.fdsfagner.challenge.framework.adapters.out.h2.entity.TransactionEntity;
import com.fdsfagner.challenge.framework.adapters.out.h2.repository.AccountRepository;
import com.fdsfagner.challenge.framework.adapters.out.h2.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TransactionAdapterOut implements TransactionPortOut {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction deposit(Account destination, BigDecimal amount) {

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setType(Transaction.Type.Deposit.name());

        Optional<AccountEntity> destinationOptional = accountRepository.findById(destination.getId());
        AccountEntity destinationtEntity;

        if (!destinationOptional.isPresent()) {
            destinationtEntity = new AccountEntity();
            destinationtEntity.setId(destination.getId());
            destinationtEntity.setBalance(BigDecimal.ZERO);
        } else {
            destinationtEntity = destinationOptional.get();
        }

        destination.setBalance(destination.getBalance());
        destination.deposit(amount);

        destinationtEntity.setBalance(destination.getBalance());
        accountRepository.saveAndFlush(destinationtEntity);

        transactionEntity.setDestination(destinationtEntity);
        transactionRepository.save(transactionEntity);

        return TransactionMapper.mapAllToDomainEntity(transactionEntity);
    }

    @Override
    public Transaction withdraw(Account origin, BigDecimal amount) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setType(Transaction.Type.Withdraw.name());

        Optional<AccountEntity> originOptional = accountRepository.findById(origin.getId());
        AccountEntity originEntity;

        if (!originOptional.isPresent()) {
            originEntity = new AccountEntity();
            originEntity.setId(origin.getId());
            originEntity.setBalance(BigDecimal.ZERO);
        } else {
            originEntity = originOptional.get();
        }

        origin.setBalance(originEntity.getBalance());
        boolean result = origin.withdraw(amount);

        if (!result){
            return null;
        }

        originEntity.setBalance(origin.getBalance());
        accountRepository.saveAndFlush(originEntity);

        transactionEntity.setOrigin(originEntity);
        transactionRepository.save(transactionEntity);

        return TransactionMapper.mapAllToDomainEntity(transactionEntity);
    }

    @Override
    public Transaction transfer(Account origin, Account destination, BigDecimal amount) {

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setType(Transaction.Type.Transfer.name());

        Optional<AccountEntity> destinationOptional = accountRepository.findById(destination.getId());
        AccountEntity destinationtEntity;

        if (!destinationOptional.isPresent()) {
            destinationtEntity = new AccountEntity();
            destinationtEntity.setId(destination.getId());
            destinationtEntity.setBalance(BigDecimal.ZERO);
        } else {
            destinationtEntity = destinationOptional.get();
        }

        destination.setBalance(destinationtEntity.getBalance());

        Optional<AccountEntity> originOptional = accountRepository.findById(origin.getId());

        if (!originOptional.isPresent()) {
            return null;
        }

        AccountEntity originEntity = originOptional.get();
        origin.setBalance(originEntity.getBalance());
        boolean result = origin.withdraw(amount);

        if (!result) {
            return null;
        }

        originEntity.setBalance(origin.getBalance());
        accountRepository.saveAndFlush(originEntity);

        destination.deposit(amount);
        destinationtEntity.setBalance(destination.getBalance());
        accountRepository.saveAndFlush(destinationtEntity);

        transactionEntity.setOrigin(originEntity);
        transactionEntity.setDestination(destinationtEntity);
        transactionRepository.save(transactionEntity);

        return TransactionMapper.mapAllToDomainEntity(transactionEntity);
    }

    @Override
    public void reset() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
    }
}
