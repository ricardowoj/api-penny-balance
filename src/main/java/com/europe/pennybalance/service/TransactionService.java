package com.europe.pennybalance.service;

import com.europe.pennybalance.enums.StatementSourceEnum;
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

    public void processTransactionFile(MultipartFile file, String statementSource) throws IOException {
        PDDocument document = Loader.loadPDF(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String pdfContent = pdfStripper.getText(document);
        document.close();
        parseAndStoreTransactions(pdfContent, statementSource);
    }

    private void parseAndStoreTransactions(String pdfContent, String statementSource) {
        if(StatementSourceEnum.TRADE_REPUBLIC.getName().equals(statementSource)) {
            transactionTradeRepublicService.parseAndStoreTransactions(pdfContent);
        }
    }
}
