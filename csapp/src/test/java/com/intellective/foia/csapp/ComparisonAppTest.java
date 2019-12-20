package com.intellective.foia.csapp;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ComparisonAppTest {

    @Autowired
    CompareController compareController;

    @Test
    void genericTest() {
        Assert.assertNotNull(compareController);

        MultipartFile[] inputFiles = Arrays.stream(new String[] {"d1.docx", "d2.pdf", "d3.txt"})
                .map(filename -> {
                    try {
                        return new MockMultipartFile(filename, filename, null, ComparisonAppTest.class.getResourceAsStream("/" + filename));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Assert.fail();
                    }
                    return null;
                }).filter(f -> f != null).toArray(MultipartFile[]::new);

        List<ComparableDocument> result = compareController.compare(inputFiles);
        result.forEach(doc -> {
            Assert.assertNotNull(doc.getGroupId());
            Assert.assertTrue(doc.getRatio() > 0);
        });
    }


/*    @Test
    void genericTest() {
        Assert.assertNotNull(comparisonService);

        *//*List<ComparableDocument> documents = Lists.newArrayList("d1.docx", "d2.pdf", "d3.txt").stream()
                .map(filename -> new ComparableDocument(filename, ComparisonServiceTest.class.getResourceAsStream("/" + filename)))
                .collect(Collectors.toList());*//*

        comparisonService.compareDocuments(() -> Lists.newArrayList("d1.docx", "d2.pdf", "d3.txt").stream()
                .map(filename -> new ComparableDocument(filename, () -> {
                    ComparisonServiceTest.class.getResourceAsStream("/"+filename)
                }))
                .collect(Collectors.toList()));

        documents.forEach(doc -> {
            Assert.assertNotNull(doc.getGroupId());
            Assert.assertTrue(doc.getRatio() > 0);
        });
    }*/

}
