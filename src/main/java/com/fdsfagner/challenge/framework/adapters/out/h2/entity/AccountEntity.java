package com.fdsfagner.challenge.framework.adapters.out.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 8825668352058118335L;

    @Id
    private long id;
    private BigDecimal balance;
}
