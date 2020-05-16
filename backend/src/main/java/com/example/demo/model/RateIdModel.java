package com.example.demo.model;

public class RateIdModel {
    private String noteId;
    private String userId;

    public RateIdModel() {
    }

    public RateIdModel(String noteId, String userId) {
        this.noteId = noteId;
        this.userId = userId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
