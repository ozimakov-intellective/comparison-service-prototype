package com.intellective.foia.csapp;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

@Component
public class TikaTextExtractorFactory implements TextExtractorFactory {

    private Tika tika = new Tika();

    @Override
    public Supplier<String> getTextExtractor(InputStream contentStream) {
        return () -> {
            try {
                return tika.parseToString(contentStream);
            } catch (Exception e) {
                throw new RuntimeException("Unable to parse content");
            }
        };
    }

}
