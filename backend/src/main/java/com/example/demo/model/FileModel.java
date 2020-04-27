package com.example.demo.model;

public class FileModel {

    protected String id;
    protected String name;
    protected String typeOfFile;
    protected String noteEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfFile() {
        return typeOfFile;
    }

    public void setTypeOfFile(String typeOfFile) {
        this.typeOfFile = typeOfFile;
    }

    public String getNoteEntity() {
        return noteEntity;
    }

    public void setNoteEntity(String noteEntity) {
        this.noteEntity = noteEntity;
    }
}
