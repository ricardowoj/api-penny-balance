package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;

import java.util.List;

public interface TradeRepublicParser {

    String EURO_SYMBOL = "€";

    List<TransactionTradeRepublicDTO> parse(String[] lines);
    void extractDate(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO);
    void extractDetails(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO);
    void extractAmount(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO);
}
