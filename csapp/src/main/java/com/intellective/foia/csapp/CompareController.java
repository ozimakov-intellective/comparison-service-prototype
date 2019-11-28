package com.intellective.foia.csapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CompareController {

    @Autowired
    ComparisonService comparisonService;

    @PostMapping(value = "/compare", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<ComparableDocument> compare(@RequestParam("files") MultipartFile[] files) {
        List<ComparableDocument> documents = Arrays.stream(files)
                .map(this::convertToDocument)
                .filter(doc -> doc != null)
                .collect(Collectors.toList());
        comparisonService.compareDocuments(documents);
        return documents;
    }

    private ComparableDocument convertToDocument(MultipartFile multipartFile) {
        try {
            return new ComparableDocument(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
