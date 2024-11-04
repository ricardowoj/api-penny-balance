package com.europe.pennybalance.service;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.util.CsvUtil;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionTradeRepublicService transactionTradeRepublicService;
    private final CsvUtil csvUtil;

    public Resource processTransactionFile(MultipartFile file) throws IOException {
        PDDocument document = Loader.loadPDF(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String pdfContent = pdfStripper.getText(document);
        document.close();
        parseAndStoreTransactions(pdfContent);
        return csvUtil.generateCsv(transactionTradeRepublicService.getAllTransactions(), TransactionTradeRepublicDTO.class);
    }

    private void parseAndStoreTransactions(String pdfContent) {
        transactionTradeRepublicService.parseAndStoreTransactions(pdfContent);
    }
}
