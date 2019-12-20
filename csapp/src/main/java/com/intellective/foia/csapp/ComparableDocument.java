package com.intellective.foia.csapp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.InputStream;
import java.util.function.Supplier;

public class ComparableDocument {

    private final String id;
    private String groupId;
    private Double ratio;

    @JsonIgnore
    private final Supplier<String> contentSupplier;

    public ComparableDocument(String id, Supplier<String> contentSupplier) {
        this.id = id;
        this.contentSupplier = contentSupplier;
    }

    public String getId() {
        return id;
    }

    public Supplier<String> getContentSupplier() {
        return contentSupplier;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

}
