package com.intellective.foia.csapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompareController {

    @Autowired
    TextExtractorFactory textExtractorFactory;

    @Autowired
    ComparisonService comparisonService;

    @PostMapping(value = "/api/compare", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ComparableDocument> compare(@RequestParam("files") MultipartFile[] files) {
        return comparisonService.compareDocuments(() -> Arrays.stream(files)
                .map(this::convertToDocument)
                .filter(doc -> doc != null)
                .collect(Collectors.toList()));
    }

    private ComparableDocument convertToDocument(MultipartFile multipartFile) {
        try {
            return new ComparableDocument(multipartFile.getOriginalFilename(), textExtractorFactory.getTextExtractor(multipartFile.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to extract documents from request");
        }
    }

}
