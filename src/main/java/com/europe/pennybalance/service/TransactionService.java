package com.europe.pennybalance.service;

import lombok.AllArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionTradeRepublicService transactionTradeRepublicService;

    public void processTransactionFile(MultipartFile file) throws IOException {
        PDDocument document = Loader.loadPDF(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String pdfContent = pdfStripper.getText(document);
        document.close();
        parseAndStoreTransactions(pdfContent);
    }

    private void parseAndStoreTransactions(String pdfContent) {
        transactionTradeRepublicService.parseAndStoreTransactions(pdfContent);
    }
}
