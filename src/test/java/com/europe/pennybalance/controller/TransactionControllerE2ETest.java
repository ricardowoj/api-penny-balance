package com.europe.pennybalance.controller;

import com.europe.pennybalance.entity.TransactionTradeRepublic;
import com.europe.pennybalance.enums.StatementSourceEnum;
import com.europe.pennybalance.enums.TradeRepublicType;
import com.europe.pennybalance.repository.TransactionTradeRepublicRepository;
import com.europe.pennybalance.util.PdfFileProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionTradeRepublicRepository transactionTradeRepublicRepository;

    @Test
    public void testUploadTransactionFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf",
                MediaType.APPLICATION_PDF_VALUE, PdfFileProvider.getStatementTradeRepublicPdf());

        mockMvc.perform(multipart("/transaction/upload")
                        .file(file)
                        .param("statementSource", StatementSourceEnum.TRADE_REPUBLIC.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("File processed successfully"));

        List<TransactionTradeRepublic> tradeRepublics = transactionTradeRepublicRepository.findAll();
        Assertions.assertFalse(tradeRepublics.isEmpty());
        for (TransactionTradeRepublic tradeRepublic : tradeRepublics) {
            Assertions.assertNotNull(tradeRepublic.getType());
            if (TradeRepublicType.getMoneyInTypes().contains(tradeRepublic.getType())) {
                Assertions.assertNotNull(tradeRepublic.getId());
                Assertions.assertNotNull(tradeRepublic.getDate());
                Assertions.assertNotNull(tradeRepublic.getDescription());
                Assertions.assertNotNull(tradeRepublic.getMoneyIn());
                Assertions.assertNotNull(tradeRepublic.getBalance());
                Assertions.assertNull(tradeRepublic.getMoneyOut());
            }
            if (TradeRepublicType.getMoneyOutTypes().contains(tradeRepublic.getType())) {
                Assertions.assertNotNull(tradeRepublic.getId());
                Assertions.assertNotNull(tradeRepublic.getDate());
                Assertions.assertNotNull(tradeRepublic.getDescription());
                Assertions.assertNotNull(tradeRepublic.getMoneyIn());
                Assertions.assertNotNull(tradeRepublic.getBalance());
                Assertions.assertNull(tradeRepublic.getMoneyOut());
            }
        }

    }
}
