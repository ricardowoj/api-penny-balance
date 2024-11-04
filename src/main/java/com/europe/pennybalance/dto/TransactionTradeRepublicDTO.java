package com.europe.pennybalance.dto;

import com.europe.pennybalance.enums.TradeRepublicType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class TransactionTradeRepublicDTO {
    @JsonProperty("ID")
    private Long id;

    @JsonProperty("Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonProperty("Type")
    private TradeRepublicType type;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Money In")
    private BigDecimal moneyIn;

    @JsonProperty("Money Out")
    private BigDecimal moneyOut;

    @JsonProperty("Balance")
    private BigDecimal balance;
}
