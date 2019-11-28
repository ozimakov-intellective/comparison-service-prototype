package com.intellective.foia.csapp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.InputStream;

public class ComparableDocument {

    private final String id;
    @JsonIgnore
    private final InputStream contentStream;
    private String groupId;
    private Double ratio;

    public ComparableDocument(String id, InputStream contentStream) {
        this.id = id;
        this.contentStream = contentStream;
    }

    public String getId() {
        return id;
    }

    public InputStream getContentStream() {
        return contentStream;
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
