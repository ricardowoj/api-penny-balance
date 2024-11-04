package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.enums.TradeRepublicType;

public class TradeRepublicTradeParser extends AbstractTradeRepublicParser {

    @Override
    protected TradeRepublicType getKey() {
        return TradeRepublicType.TRADE;
    }
}
