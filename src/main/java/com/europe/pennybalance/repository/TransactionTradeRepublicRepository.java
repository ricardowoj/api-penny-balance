package com.europe.pennybalance.repository;

import com.europe.pennybalance.entity.TransactionTradeRepublic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionTradeRepublicRepository extends JpaRepository<TransactionTradeRepublic, Long> {
    List<TransactionTradeRepublic> findAllByOrderByDateAsc();
}
