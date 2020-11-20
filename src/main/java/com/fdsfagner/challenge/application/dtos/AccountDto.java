package com.fdsfagner.challenge.application.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private String id;
    private BigDecimal balance;
}
