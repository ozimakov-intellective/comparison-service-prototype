package com.intellective.foia.csapp;

import java.util.List;
import java.util.function.Supplier;

public interface ComparisonService {

    List<ComparableDocument> compareDocuments(Supplier<List<ComparableDocument>> docListSupplier);

}
