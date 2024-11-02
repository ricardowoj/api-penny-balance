package com.europe.pennybalance.enums;

import lombok.Getter;

@Getter
public enum StatementSourceEnum {
    TRADE_REPUBLIC("trade-republic");

    private final String name;

    StatementSourceEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static StatementSourceEnum findByName(String name) {
        for (StatementSourceEnum source : values()) {
            if (source.getName().equalsIgnoreCase(name)) {
                return source;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}