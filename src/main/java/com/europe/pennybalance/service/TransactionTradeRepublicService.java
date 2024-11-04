package com.europe.pennybalance.service;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.entity.TransactionTradeRepublic;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.mapper.TransactionTradeRepublicMapper;
import com.europe.pennybalance.repository.TransactionTradeRepublicRepository;
import com.europe.pennybalance.service.parsePdf.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransactionTradeRepublicService {

    private final TransactionTradeRepublicRepository repository;
    private final TransactionTradeRepublicMapper mapper;

    public void saveTransaction(TransactionTradeRepublicDTO transactionDTO) {
        TransactionTradeRepublic transaction = mapper.toEntity(transactionDTO);
        repository.save(transaction);
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
        Map<TradeRepublicType, TradeRepublicParser> parserMap = new HashMap<>();
        parserMap.put(TradeRepublicType.TRANSFER, new TradeRepublicTransferParser());
        //parserMap.put(TradeRepublicType.TRADE, new TradeRepublicTradeParser());
        //parserMap.put(TradeRepublicType.CARD_TRANSACTION, new TradeRepublicCardTransactionParser());
        //parserMap.put(TradeRepublicType.INTEREST_PAYMENT, new TradeRepublicInterestPaymentParser());
        //parserMap.put(TradeRepublicType.REWARD, new TradeRepublicRewardParser());
        for (TradeRepublicType type : TradeRepublicType.values()) {
            TradeRepublicParser parser = parserMap.get(type);
            if (parser != null) {
                List<TransactionTradeRepublicDTO> parsedTransactions = parser.parse(lines);
                transactionListDTO.addAll(parsedTransactions);
            }
        }
        transactionListDTO.forEach(this::saveTransaction);
    }
}
