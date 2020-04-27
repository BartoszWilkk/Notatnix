package com.example.demo.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    protected String typeOfFile;

    @Lob
    protected byte[] data;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "note_id")
    protected NoteEntity noteEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public NoteEntity getNoteEntity() {
        return noteEntity;
    }

    public void setNoteEntity(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
    }
}
