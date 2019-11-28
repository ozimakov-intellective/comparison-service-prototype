package com.intellective.foia.csapp;

import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ElementType;
import com.intuit.fuzzymatcher.domain.Match;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IntuitFuzzyMatcherComparisonServiceImpl implements ComparisonService {

    private Tika tika = new Tika();
    private MatchService matchService = new MatchService();

    public void compareDocuments(List<ComparableDocument> documents) {
        List<Document> matchDocs = documents.stream().map(doc -> {
            try {
                return new Document.Builder(doc.getId())
                        .addElement(
                                new Element.Builder().setType(ElementType.TEXT)
                                        .setValue(tika.parseToString(doc.getContentStream()))
                                        .createElement())
                        .setThreshold(0.5)
                        .createDocument();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).filter(doc -> doc != null).collect(Collectors.toList());

        Map<String,ComparableDocument> documentMap = documents.stream().collect(Collectors.toMap(ComparableDocument::getId, Function.identity()));
        Map<Document, List<Match<Document>>> allMatches = matchService.applyMatch(matchDocs);

        // TODO: implement tree traversal and global ratio calculation to get universal algorithm
        allMatches.values().stream().flatMap(l -> l.stream()).forEach(m -> {
            ComparableDocument d1 = documentMap.get(m.getData().getKey());
            ComparableDocument d2 = documentMap.get(m.getMatchedWith().getKey());

            if (d1.getGroupId() == null) {
                if (d2.getGroupId() == null) {
                    d1.setGroupId(UUID.randomUUID().toString());
                    d1.setRatio(1.0);
                    d2.setGroupId(d1.getGroupId());
                    d2.setRatio(m.getResult());
                } else {
                    d1.setGroupId(d2.getGroupId());
                    d1.setRatio(m.getResult());
                }
            } else {
                if (d2.getGroupId() == null) {
                    d2.setGroupId(d1.getGroupId());
                    d2.setRatio(m.getResult());
                }
            }
        });

        documents.stream().forEach(doc -> {
            if (doc.getGroupId() == null) {
                doc.setGroupId(UUID.randomUUID().toString());
                doc.setRatio(1.0);
            }
        });


    }

}
