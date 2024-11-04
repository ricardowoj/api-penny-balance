package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.enums.TradeRepublicType;

public class TradeRepublicTransferParser extends AbstractTradeRepublicParser {

    @Override
    protected TradeRepublicType getKey() {
        return TradeRepublicType.TRANSFER;
    }
}
