package com.europe.pennybalance.controller;


import com.europe.pennybalance.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Resource> uploadTransactionFile(@RequestParam("file") MultipartFile file) throws IOException {
        Resource resource = transactionService.processTransactionFile(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=transactions.csv");
        headers.add("Content-Type", "text/csv");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
