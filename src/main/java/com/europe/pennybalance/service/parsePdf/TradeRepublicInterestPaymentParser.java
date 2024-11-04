package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.enums.TradeRepublicType;

public class TradeRepublicInterestPaymentParser extends AbstractTradeRepublicParser {

    @Override
    protected TradeRepublicType getKey() {
        return TradeRepublicType.INTEREST_PAYMENT;
    }
}
