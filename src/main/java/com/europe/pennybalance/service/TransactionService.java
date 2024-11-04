package com.europe.pennybalance.service;

import com.europe.pennybalance.dto.TransactionTradeRepublicDTO;
import com.europe.pennybalance.util.CsvUtil;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionTradeRepublicService transactionTradeRepublicService;
    private final CsvUtil csvUtil;

    public Resource processTransactionFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The file is empty.");
        }
        if (!"application/pdf".equalsIgnoreCase(file.getContentType())) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported file type. Please upload a PDF file.");
        }
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
