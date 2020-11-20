package com.fdsfagner.challenge.application.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventDto {

    private String type;
    private String origin;
    private String destination;
    private BigDecimal amount;
}
