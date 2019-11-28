package com.intellective.foia.csapp;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ComparisonServiceTest {

    @Autowired
    ComparisonService comparisonService;

    @Test
    void genericTest() {
        Assert.assertNotNull(comparisonService);

        List<ComparableDocument> documents = Lists.newArrayList("d1.docx", "d2.pdf", "d3.txt").stream()
                .map(filename -> new ComparableDocument(filename, ComparisonServiceTest.class.getResourceAsStream("/" + filename)))
                .collect(Collectors.toList());

        comparisonService.compareDocuments(documents);

        documents.forEach(doc -> {
            Assert.assertNotNull(doc.getGroupId());
            Assert.assertTrue(doc.getRatio() > 0);
        });
    }

}
