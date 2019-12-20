package com.intellective.foia.csapp;

import java.io.InputStream;
import java.util.function.Supplier;

public interface TextExtractorFactory {

    Supplier<String> getTextExtractor(InputStream contentStream);

}
