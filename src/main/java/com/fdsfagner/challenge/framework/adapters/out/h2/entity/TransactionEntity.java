package com.fdsfagner.challenge.framework.adapters.out.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionEntity implements Serializable {

    private static final long serialVersionUID = 8825668352058118335L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private AccountEntity origin;

    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private AccountEntity destination;

    private BigDecimal amount;

    private String type;
}
