package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.enums.DateFormatTypeEnum;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.util.BigDecimalUtil;
import com.europe.pennybalance.util.DateFormatUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TradeRepublicTradeParser implements TradeRepublicParser {
    private static final TradeRepublicType KEY = TradeRepublicType.TRADE;

    @Override
    public List<TransactionTradeRepublicDTO> parse(String[] lines) {
        List<TransactionTradeRepublicDTO> transfersDTO = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String type = lines[i - 1].trim();
            if (type.equals(KEY.getName())) {
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
        String line1 = lines[currentIndex];
        String line2 = lines[currentIndex + 1];
        transactionDTO.setDescription(line1 + line2);
    }

    @Override
    public void extractAmount(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO) {
        String lineAmount = lines[currentIndex + 2];
        int firstEuroIndex = lineAmount.indexOf(EURO_SYMBOL);
        int secondEuroIndex = lineAmount.indexOf(EURO_SYMBOL, firstEuroIndex + 1);
        if (firstEuroIndex != -1 && secondEuroIndex != -1) {
            String firstAmount = lineAmount.substring(firstEuroIndex + 1, secondEuroIndex).trim();
            String secondAmount = lineAmount.substring(secondEuroIndex + 1).trim();
            transactionDTO.setMoneyOut(BigDecimalUtil.normalize(firstAmount));
            transactionDTO.setBalance(BigDecimalUtil.normalize(secondAmount));
        }
    }
}
