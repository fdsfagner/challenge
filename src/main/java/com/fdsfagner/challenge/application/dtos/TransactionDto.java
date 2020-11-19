package com.fdsfagner.challenge.application.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class TransactionDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AccountDto origin;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AccountDto destination;
}
