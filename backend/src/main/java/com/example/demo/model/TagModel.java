package com.example.demo.model;

public class TagModel {

    private String id;
    private String tagName;

    public TagModel() {
    }

    public TagModel(String tagName) {
        this.tagName = tagName;
    }

    public TagModel(String id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
