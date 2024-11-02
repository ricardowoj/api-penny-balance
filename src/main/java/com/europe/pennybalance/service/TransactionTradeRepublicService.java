package com.europe.pennybalance.service;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.entity.TransactionTradeRepublic;
import com.europe.pennybalance.enums.DateFormatTypeEnum;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.mapper.TransactionTradeRepublicMapper;
import com.europe.pennybalance.repository.TransactionTradeRepublicRepository;
import com.europe.pennybalance.util.BigDecimalUtil;
import com.europe.pennybalance.util.DateFormatUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

@AllArgsConstructor
@Service
public class TransactionTradeRepublicService {

    private static final String EURO_SYMBOL = "€";
    private static final String TRANSFER_KEY = TradeRepublicType.TRANSFER.getName();

    private final TransactionTradeRepublicRepository repository;
    private final TransactionTradeRepublicMapper mapper;

    public TransactionTradeRepublicDTO saveTransaction(TransactionTradeRepublicDTO transactionDTO) {
        TransactionTradeRepublic transaction = mapper.toEntity(transactionDTO);
        TransactionTradeRepublic savedTransaction = repository.save(transaction);
        return mapper.toDTO(savedTransaction);
    }

    public List<TransactionTradeRepublicDTO> getAllTransactions() {
        List<TransactionTradeRepublic> transactions = repository.findAll();
        return transactions.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public TransactionTradeRepublicDTO getTransactionById(Long id) {
        TransactionTradeRepublic transaction = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found with ID: " + id));
        return mapper.toDTO(transaction);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public void parseAndStoreTransactions(String pdfContent) {
        String[] lines = pdfContent.split("\n");
        List<TransactionTradeRepublicDTO> transactionListDTO = new ArrayList<>();
        for (TradeRepublicType type : TradeRepublicType.values()) {
            if (type == TradeRepublicType.TRANSFER) {
                List<TransactionTradeRepublicDTO> transfersDTO = parseTransfer(lines);
                transactionListDTO.addAll(transfersDTO);
            }
        }
        transactionListDTO.forEach(this::saveTransaction);
    }

    private List<TransactionTradeRepublicDTO> parseTransfer(String[] lines) {
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

    private LocalDate extractDate(String[] lines, int currentIndex) {
        String dayMonth = lines[currentIndex - 2].trim();
        String year = lines[currentIndex - 1].trim();
        String date = dayMonth + " " + year;
        if (DateFormatUtil.isValid(DateFormatTypeEnum.DD_MMM_YYYY, date)) {
            return DateFormatUtil.parse(DateFormatTypeEnum.DD_MMM_YYYY, date);
        }
        return null;
    }

    private void extractTransactionDetails(String line, TransactionTradeRepublicDTO transactionDTO) {
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
