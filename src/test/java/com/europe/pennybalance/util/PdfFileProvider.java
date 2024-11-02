package com.europe.pennybalance.util;

import java.io.IOException;
import java.io.InputStream;

public class PdfFileProvider {

    public static InputStream getStatementTradeRepublicPdf() throws IOException {
        return TestFileUtil.loadPdfFile("statement-trade-republic.pdf");
    }
}
