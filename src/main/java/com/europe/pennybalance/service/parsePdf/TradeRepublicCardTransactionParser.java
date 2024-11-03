package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.enums.DateFormatTypeEnum;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.util.BigDecimalUtil;
import com.europe.pennybalance.util.DateFormatUtil;

import java.util.ArrayList;
import java.util.List;

public class TradeRepublicCardTransactionParser implements TradeRepublicParser {

    private static final TradeRepublicType KEY = TradeRepublicType.CARD_TRANSACTION;

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
        String builderDescription = buildDescriptionUntilSymbol(lines, currentIndex);
        int firstEuroIndex = builderDescription.indexOf(EURO_SYMBOL);
        String description = builderDescription.substring(0, firstEuroIndex).trim();
        transactionDTO.setDescription(description);
    }

    @Override
    public void extractAmount(String[] lines, int currentIndex, TransactionTradeRepublicDTO transactionDTO) {
        String builderDescription = buildDescriptionUntilSymbol(lines, currentIndex);
        int firstEuroIndex = builderDescription.indexOf(EURO_SYMBOL);
        int secondEuroIndex = builderDescription.indexOf(EURO_SYMBOL, firstEuroIndex + 1);
        if (firstEuroIndex != -1 && secondEuroIndex != -1) {
            String firstAmount = builderDescription.substring(firstEuroIndex + 1, secondEuroIndex).trim();
            String secondAmount = builderDescription.substring(secondEuroIndex + 1).trim();
            transactionDTO.setMoneyOut(BigDecimalUtil.normalize(firstAmount));
            transactionDTO.setBalance(BigDecimalUtil.normalize(secondAmount));
        }
    }

    private String buildDescriptionUntilSymbol(String[] lines, int currentIndex) {
        int lineIndex = 1;
        StringBuilder descriptionBuilder = new StringBuilder();
        for (int i = currentIndex; i < lines.length; i++) {
            String line = lines[currentIndex + lineIndex];
            descriptionBuilder.append(line);
            if(line.contains(EURO_SYMBOL)) {
                break;
            } else {
                lineIndex++;
            }
        }
        return descriptionBuilder.toString();
    }
}
