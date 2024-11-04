package com.europe.pennybalance.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CsvUtil {

    private final CsvMapper csvMapper;

    public <T> Resource generateCsv(List<T> data, Class<T> clazz) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
        csvMapper.writer(schema).writeValue(outputStream, data);
        return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
    }

    public static <T> List<T> readCsvToList(InputStream inputStream, Class<T> clazz) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(new JavaTimeModule());
        CsvSchema schema = csvMapper.schemaFor(clazz).withHeader();
        List<T> resultList = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            MappingIterator<T> iterator = csvMapper.readerFor(clazz).with(schema).readValues(reader);
            while (iterator.hasNext()) {
                resultList.add(iterator.next());
            }
        }
        return resultList;
    }
}