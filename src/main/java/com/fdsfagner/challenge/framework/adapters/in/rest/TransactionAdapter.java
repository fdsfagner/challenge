package com.fdsfagner.challenge.framework.adapters.in.rest;

import com.fdsfagner.challenge.application.dtos.AccountDto;
import com.fdsfagner.challenge.application.dtos.EventDto;
import com.fdsfagner.challenge.application.dtos.TransactionDto;
import com.fdsfagner.challenge.application.queries.AccountQuery;
import com.fdsfagner.challenge.application.queries.TransationQuery;
import com.fdsfagner.challenge.domain.Account;
import com.fdsfagner.challenge.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransactionAdapter {

    @Autowired
    private AccountQuery accountQuery;

    @Autowired
    private TransationQuery transationQuery;

    @PostMapping("/event")
    public @ResponseBody
    ResponseEntity<?> post(@RequestBody EventDto event) {
        TransactionDto dto = new TransactionDto();

        AccountDto destinationDto = new AccountDto();
        destinationDto.setId(event.getDestination());

        AccountDto originDto = new AccountDto();
        originDto.setId(event.getOrigin());

        Account destination = null;

        if (!ObjectUtils.isEmpty(event.getDestination())) {
            destination = accountQuery.getAccountById(Long.valueOf(event.getDestination()));
        }

        if (destination == null && !ObjectUtils.isEmpty(event.getDestination())) {
            destination = new Account();
            destination.setId(Long.valueOf(event.getDestination()));
            destination.setBalance(BigDecimal.ZERO);
        }

        Account origin = null;

        if (!ObjectUtils.isEmpty(event.getOrigin())) {
            origin = accountQuery.getAccountById(Long.valueOf(event.getOrigin()));
        }

        Transaction transaction;

        if (event.getType().equalsIgnoreCase(Transaction.Type.Deposit.name())){
            transaction = transationQuery.deposit(destination, event.getAmount());

            dto.setDestination(destinationDto);
            dto.getDestination().setBalance(transaction.getDestination().getBalance());
        } else if (origin != null) {
            if (event.getType().equalsIgnoreCase(Transaction.Type.Withdraw.name())) {
                transaction = transationQuery.withdraw(origin, event.getAmount());

                if (transaction == null) {
                    return new ResponseEntity<String>("0", HttpStatus.NOT_FOUND);
                }

                dto.setOrigin(originDto);
                dto.getOrigin().setBalance(transaction.getOrigin().getBalance());
            } else if (event.getType().equalsIgnoreCase(Transaction.Type.Transfer.name())) {
                transaction = transationQuery.transfer(origin, destination, event.getAmount());

                if (transaction == null) {
                    return new ResponseEntity<String>("0", HttpStatus.NOT_FOUND);
                }

                dto.setOrigin(originDto);
                dto.setDestination(destinationDto);
                dto.getOrigin().setBalance(transaction.getOrigin().getBalance());
                dto.getDestination().setBalance(transaction.getDestination().getBalance());
            }
        } else {
            return new ResponseEntity<String>("0", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<TransactionDto>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/reset")
    ResponseEntity<String> post() {
        transationQuery.reset();
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

}
