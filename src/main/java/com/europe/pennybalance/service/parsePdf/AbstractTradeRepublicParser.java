package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.enums.BankTransactionTypeMoney;
import com.europe.pennybalance.enums.DateFormatTypeEnum;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.util.BigDecimalUtil;
import com.europe.pennybalance.util.DateFormatUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTradeRepublicParser implements TradeRepublicParser {

    protected abstract TradeRepublicType getKey();

    @Override
    public List<TransactionTradeRepublicDTO> parse(String[] lines) {
        List<TransactionTradeRepublicDTO> transfersDTO = new ArrayList<>();
        for (int i = 10; i < lines.length; i++) {
            String line = lines[i];
            boolean startWithKey = line.startsWith(getKey().getName());
            boolean isYear = DateFormatUtil.isValid(DateFormatTypeEnum.YYYY, lines[i - 1]);
            if(!isYear && startWithKey && getKey().equals(TradeRepublicType.TRADE)) {
                isYear = DateFormatUtil.isValid(DateFormatTypeEnum.YYYY, lines[i - 2]);
            }
            if (startWithKey && isYear) {
                String completeDescription = buildCompleteDescription(lines, i);
                if(getKey().equals(TradeRepublicType.TRADE)) {
                    System.out.println();
                }
                TransactionTradeRepublicDTO transferDTO = new TransactionTradeRepublicDTO();
                transferDTO.setType(getKey());
                extractDate(completeDescription, transferDTO);
                extractDetails(completeDescription, transferDTO);
                extractAmount(completeDescription, transferDTO);
                transfersDTO.add(transferDTO);
            }
        }
        return transfersDTO;
    }

    private String buildCompleteDescription(String[] lines, int currentIndex) {
        boolean addDate = false;
        StringBuilder descriptionBuilder = new StringBuilder();
        for (int i = currentIndex; i < lines.length; i++) {
            if(!addDate) {
                String dayMonth = lines[currentIndex - 2].trim();
                String year = lines[currentIndex - 1].trim();
                String date = dayMonth + " " + year + " ";
                descriptionBuilder.append(date);
                addDate = true;
            }
            String line = lines[i];
            descriptionBuilder.append(line + " ");
            if(line.contains(EURO_SYMBOL)) {
                break;
            }
        }
        return descriptionBuilder.toString();
    }

    @Override
    public void extractDate(String completeDescription, TransactionTradeRepublicDTO transactionDTO) {
        String date = completeDescription.substring(0, 11).trim();
        if (DateFormatUtil.isValid(DateFormatTypeEnum.DD_MMM_YYYY, date)) {
            transactionDTO.setDate(DateFormatUtil.parse(DateFormatTypeEnum.DD_MMM_YYYY, date));
        }
    }

    @Override
    public void extractDetails(String completeDescription, TransactionTradeRepublicDTO transactionDTO) {
        int startIndex = completeDescription.indexOf(getKey().getName());
        String cleanedDetails = completeDescription.substring(startIndex).trim();
        int euroIndex = cleanedDetails.indexOf("€");
        if (euroIndex != -1) {
            cleanedDetails = cleanedDetails.substring(0, euroIndex).trim();
        }
        transactionDTO.setDescription(cleanedDetails);
    }

    @Override
    public void extractAmount(String completeDescription, TransactionTradeRepublicDTO transactionDTO) {
        int firstEuroIndex = completeDescription.indexOf(EURO_SYMBOL);
        int secondEuroIndex = completeDescription.indexOf(EURO_SYMBOL, firstEuroIndex + 1);
        if (firstEuroIndex != -1 && secondEuroIndex != -1) {
            String firstAmount = completeDescription.substring(firstEuroIndex + 1, secondEuroIndex).trim();
            String secondAmount = completeDescription.substring(secondEuroIndex + 1).trim();
            if(getKey().getTypeMoney().equals(BankTransactionTypeMoney.MONEY_IN)) {
                transactionDTO.setMoneyIn(BigDecimalUtil.normalize(firstAmount));
            }
            if(getKey().getTypeMoney().equals(BankTransactionTypeMoney.MONEY_OUT)) {
                transactionDTO.setMoneyOut(BigDecimalUtil.normalize(firstAmount));
            }
            transactionDTO.setBalance(BigDecimalUtil.normalize(secondAmount));
        }
    }
}
