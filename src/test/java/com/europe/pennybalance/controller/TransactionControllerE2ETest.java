package com.europe.pennybalance.controller;

import com.europe.pennybalance.enums.StatementSource;
import com.europe.pennybalance.util.PdfFileProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadTransactionFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf",
                MediaType.APPLICATION_PDF_VALUE, PdfFileProvider.getStatementTradeRepublicPdf());

        mockMvc.perform(multipart("/transaction/upload")
                        .file(file)
                        .param("statementSource", StatementSource.TRADE_REPUBLIC.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("File processed successfully"));
    }
}