package com.example.demo.model;

public class RateModel {

    private String userId;
    private String noteId;
    private String rate;

    public RateModel() {
    }

    public RateModel(String userId, String noteId, String rate) {
        this.userId = userId;
        this.noteId = noteId;
        this.rate = rate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
