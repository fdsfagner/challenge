package com.fdsfagner.challenge.application.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventDto {

    private String type;
    private long origin;
    private long destination;
    private BigDecimal amount;
}
