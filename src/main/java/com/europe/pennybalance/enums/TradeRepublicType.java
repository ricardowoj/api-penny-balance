package com.europe.pennybalance.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TradeRepublicType {
    TRANSFER("Transfer"),
    TRADE("Trade"),
    CARD_TRANSACTION("Transaction"),
    INTEREST_PAYMENT("Payment"),
    REWARD("Reward");

    private final String name;

    TradeRepublicType(String name) {
        this.name = name;
    }

    public static TradeRepublicType findByName(String name) {
        return Arrays.stream(TradeRepublicType.values())
                .filter(type -> type.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
