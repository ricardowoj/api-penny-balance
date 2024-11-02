package com.europe.pennybalance.enums;

import lombok.Getter;

@Getter
public enum StatementSource {
    TRADE_REPUBLIC("trade-republic");

    private final String name;

    StatementSource(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static StatementSource findByName(String name) {
        for (StatementSource source : values()) {
            if (source.getName().equalsIgnoreCase(name)) {
                return source;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}