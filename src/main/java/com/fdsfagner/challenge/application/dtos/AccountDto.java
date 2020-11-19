package com.fdsfagner.challenge.application.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private long id;
    private BigDecimal balance;
}
