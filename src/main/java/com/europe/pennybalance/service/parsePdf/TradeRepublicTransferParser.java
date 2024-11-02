package com.europe.pennybalance.service.parsePdf;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.enums.DateFormatTypeEnum;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.util.BigDecimalUtil;
import com.europe.pennybalance.util.DateFormatUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TradeRepublicTransferParser implements TradeRepublicParser {

    private static final String EURO_SYMBOL = "€";
    private static final String TRANSFER_KEY = TradeRepublicType.TRANSFER.getName();

    @Override
    public List<TransactionTradeRepublicDTO> parse(String[] lines) {
        List<TransactionTradeRepublicDTO> transfersDTO = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.startsWith(TRANSFER_KEY)) {
                line = line.substring(TRANSFER_KEY.length()).trim();
                TransactionTradeRepublicDTO transferDTO = new TransactionTradeRepublicDTO();
                transferDTO.setType(TradeRepublicType.TRANSFER);
                transferDTO.setDate(extractDate(lines, i));
                extractTransactionDetails(line, transferDTO);
                transfersDTO.add(transferDTO);

            };
        }
        return transfersDTO;
    }

    @Override
    public LocalDate extractDate(String[] lines, int currentIndex) {
        String dayMonth = lines[currentIndex - 2].trim();
        String year = lines[currentIndex - 1].trim();
        String date = dayMonth + " " + year;
        if (DateFormatUtil.isValid(DateFormatTypeEnum.DD_MMM_YYYY, date)) {
            return DateFormatUtil.parse(DateFormatTypeEnum.DD_MMM_YYYY, date);
        }
        return null;
    }

    @Override
    public void extractTransactionDetails(String line, TransactionTradeRepublicDTO transactionDTO) {
        int firstEuroIndex = line.indexOf(EURO_SYMBOL);
        int secondEuroIndex = line.indexOf(EURO_SYMBOL, firstEuroIndex + 1);

        if (firstEuroIndex == -1 || secondEuroIndex == -1) return;

        String description = line.substring(0, firstEuroIndex).trim();
        String firstAmount = line.substring(firstEuroIndex + 1, secondEuroIndex).trim();
        String secondAmount = line.substring(secondEuroIndex + 1).trim();

        transactionDTO.setDescription(description);
        transactionDTO.setMoneyIn(BigDecimalUtil.normalize(firstAmount));
        transactionDTO.setBalance(BigDecimalUtil.normalize(secondAmount));
    }
}
