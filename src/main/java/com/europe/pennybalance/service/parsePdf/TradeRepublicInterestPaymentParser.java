package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.enums.DateFormatTypeEnum;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.util.BigDecimalUtil;
import com.europe.pennybalance.util.DateFormatUtil;

import java.util.ArrayList;
import java.util.List;

public class TradeRepublicInterestPaymentParser implements TradeRepublicParser {

    private static final TradeRepublicType KEY = TradeRepublicType.INTEREST_PAYMENT;

    @Override
    public List<TransactionTradeRepublicDTO> parse(String[] lines) {
        List<TransactionTradeRepublicDTO> transfersDTO = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith(KEY.getName())) {
                TransactionTradeRepublicDTO transferDTO = new TransactionTradeRepublicDTO();
                transferDTO.setType(KEY);
                extractDate(lines, i, transferDTO);
                extractDetails(lines, i, transferDTO);
                extractAmount(lines, i, transferDTO);
                transfersDTO.add(transferDTO);
            };
        }
        return transfersDTO;
    }

    @Override
    public void extractDate(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO) {
        String dayMonth = lines[currentIndex - 3].trim();
        String year = lines[currentIndex - 2].trim();
        String date = dayMonth + " " + year;
        if (DateFormatUtil.isValid(DateFormatTypeEnum.DD_MMM_YYYY, date)) {
            transactionDTO.setDate(DateFormatUtil.parse(DateFormatTypeEnum.DD_MMM_YYYY, date));
        }
    }

    @Override
    public void extractDetails(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO) {
        String line = lines[currentIndex + 1];
        int firstEuroIndex = line.indexOf(EURO_SYMBOL);
        String description = line.substring(0, firstEuroIndex).trim();
        transactionDTO.setDescription(description);
    }

    @Override
    public void extractAmount(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO) {
        String line = lines[currentIndex + 1];
        int firstEuroIndex = line.indexOf(EURO_SYMBOL);
        int secondEuroIndex = line.indexOf(EURO_SYMBOL, firstEuroIndex + 1);
        if (firstEuroIndex != -1 && secondEuroIndex != -1) {
            String firstAmount = line.substring(firstEuroIndex + 1, secondEuroIndex).trim();
            String secondAmount = line.substring(secondEuroIndex + 1).trim();
            transactionDTO.setMoneyIn(BigDecimalUtil.normalize(firstAmount));
            transactionDTO.setBalance(BigDecimalUtil.normalize(secondAmount));
        }
    }
}
