package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.enums.TradeRepublicType;

public class TradeRepublicRewardParser extends AbstractTradeRepublicParser {

    @Override
    protected TradeRepublicType getKey() {
        return TradeRepublicType.REWARD;
    }
}
