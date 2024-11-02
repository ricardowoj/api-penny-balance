package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;

import java.time.LocalDate;
import java.util.List;

public interface TradeRepublicParser {
    List<TransactionTradeRepublicDTO> parse(String[] lines);
    LocalDate extractDate(String[] lines, int currentIndex);
    void extractTransactionDetails(String line, TransactionTradeRepublicDTO transactionDTO);
}
