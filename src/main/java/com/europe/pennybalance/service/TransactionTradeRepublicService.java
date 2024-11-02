package com.europe.pennybalance.service;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.entity.TransactionTradeRepublic;
import com.europe.pennybalance.mapper.TransactionTradeRepublicMapper;
import com.europe.pennybalance.repository.TransactionTradeRepublicRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransactionTradeRepublicService {

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
}
