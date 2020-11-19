package com.fdsfagner.challenge.framework.adapters.in.rest;

import com.fdsfagner.challenge.application.queries.AccountQuery;
import com.fdsfagner.challenge.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountAdapter {

    @Autowired
    private AccountQuery accountQuery;

    @GetMapping(path = "/balance")
    ResponseEntity<String> getBalance(@RequestParam("account_id") String account_id){

        Account account = accountQuery.getAccountById(new Long(account_id));

        if (account != null)
        {
            return new ResponseEntity<String>(account.getBalance().toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("0", HttpStatus.NOT_FOUND);
        }
    }
}
