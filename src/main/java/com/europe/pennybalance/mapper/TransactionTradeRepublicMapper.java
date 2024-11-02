package com.europe.pennybalance.mapper;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.entity.TransactionTradeRepublic;
import org.springframework.stereotype.Component;

@Component
public class TransactionTradeRepublicMapper {

    public TransactionTradeRepublicDTO toDTO(TransactionTradeRepublic transaction) {
        if (transaction == null) {
            return null;
        }
        TransactionTradeRepublicDTO dto = new TransactionTradeRepublicDTO();
        dto.setId(transaction.getId());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        dto.setDescription(transaction.getDescription());
        dto.setMoneyIn(transaction.getMoneyIn());
        dto.setMoneyOut(transaction.getMoneyOut());
        dto.setBalance(transaction.getBalance());
        return dto;
    }

    public TransactionTradeRepublic toEntity(TransactionTradeRepublicDTO dto) {
        if (dto == null) {
            return null;
        }
        TransactionTradeRepublic transaction = new TransactionTradeRepublic();
        transaction.setId(dto.getId());
        transaction.setDate(dto.getDate());
        transaction.setType(dto.getType());
        transaction.setDescription(dto.getDescription());
        transaction.setMoneyIn(dto.getMoneyIn());
        transaction.setMoneyOut(dto.getMoneyOut());
        transaction.setBalance(dto.getBalance());
        return transaction;
    }
}
