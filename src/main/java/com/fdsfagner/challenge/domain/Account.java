package com.fdsfagner.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    private long id;
    private BigDecimal balance;

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public boolean withdraw(BigDecimal amount) {

        BigDecimal results =  this.balance.subtract(amount);

        if (results.longValue() >= 0) {
            this.balance = this.balance.subtract(amount);
            return true;
        }

        return false;
    }
}
