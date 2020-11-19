package com.fdsfagner.challenge.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Transaction {

    private long id;
    private Account origin;
    private Account destination;
    private BigDecimal amount;

    public enum Type {

        Deposit("deposit"),
        Withdraw("withdraw"),
        Transfer("transfer");

        private String type;

        Type(String type) {
            this.type = type;
        }
    }
}
