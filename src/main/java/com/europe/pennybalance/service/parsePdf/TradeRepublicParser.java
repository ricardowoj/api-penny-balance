package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;

import java.util.List;

public interface TradeRepublicParser {

    String EURO_SYMBOL = "€";

    List<TransactionTradeRepublicDTO> parse(String[] lines);
    void extractDate(String completeDescription, TransactionTradeRepublicDTO transactionDTO);
    void extractDetails(String completeDescription, TransactionTradeRepublicDTO transactionDTO);
    void extractAmount(String completeDescription, TransactionTradeRepublicDTO transactionDTO);
}
