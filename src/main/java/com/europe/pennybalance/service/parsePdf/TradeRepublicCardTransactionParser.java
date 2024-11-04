package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.enums.TradeRepublicType;

public class TradeRepublicCardTransactionParser extends AbstractTradeRepublicParser {

    @Override
    protected TradeRepublicType getKey() {
        return TradeRepublicType.CARD_TRANSACTION;
    }
}
