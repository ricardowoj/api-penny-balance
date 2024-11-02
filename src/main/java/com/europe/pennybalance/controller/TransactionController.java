package com.europe.pennybalance.controller;


import com.europe.pennybalance.service.TransactionService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTransactionFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("statementSource") String statementSource) {
        try {
            transactionService.processTransactionFile(file, statementSource);
            return ResponseEntity.status(HttpStatus.OK).body("File processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process file: " + e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("TransactionController initialized");
    }
}
