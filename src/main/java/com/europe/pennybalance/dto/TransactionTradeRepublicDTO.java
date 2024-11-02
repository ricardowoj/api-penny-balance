package com.europe.pennybalance.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class TransactionTradeRepublicDTO {
    private Long id;
    private LocalDate date;
    private String type;
    private String description;
    private BigDecimal moneyIn;
    private BigDecimal moneyOut;
    private BigDecimal balance;
}
