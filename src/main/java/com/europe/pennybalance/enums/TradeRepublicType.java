package com.europe.pennybalance.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum TradeRepublicType {
    TRANSFER("Transfer", BankTransactionTypeMoney.MONEY_IN),
    TRADE("Trade", BankTransactionTypeMoney.MONEY_OUT),
    CARD_TRANSACTION("Transaction", BankTransactionTypeMoney.MONEY_OUT),
    INTEREST_PAYMENT("Payment", BankTransactionTypeMoney.MONEY_IN),
    REWARD("Reward", BankTransactionTypeMoney.MONEY_IN);

    private final String name;
    private final BankTransactionTypeMoney typeMoney;

    TradeRepublicType(String name, BankTransactionTypeMoney typeMoney) {
        this.name = name;
        this.typeMoney = typeMoney;
    }

    public static TradeRepublicType findByName(String name) {
        return Arrays.stream(TradeRepublicType.values())
                .filter(type -> type.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static List<TradeRepublicType> getMoneyInTypes() {
        return Arrays.stream(TradeRepublicType.values())
                .filter(type -> type.typeMoney == BankTransactionTypeMoney.MONEY_IN)
                .collect(Collectors.toList());
    }

    public static List<TradeRepublicType> getMoneyOutTypes() {
        return Arrays.stream(TradeRepublicType.values())
                .filter(type -> type.typeMoney == BankTransactionTypeMoney.MONEY_OUT)
                .collect(Collectors.toList());
    }
}
