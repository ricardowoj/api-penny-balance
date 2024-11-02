package com.europe.pennybalance.entity;

import com.europe.pennybalance.enums.TradeRepublicType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class TransactionTradeRepublic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private TradeRepublicType type;

    private String description;

    private BigDecimal moneyIn;

    private BigDecimal moneyOut;

    private BigDecimal balance;

}
