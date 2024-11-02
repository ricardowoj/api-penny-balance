package com.europe.pennybalance.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class TestFileUtil {

    public static InputStream loadPdfFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("pdfs/" + fileName);
        return resource.getInputStream();
    }
}
