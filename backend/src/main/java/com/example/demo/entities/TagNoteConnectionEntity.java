package com.example.demo.entities;


import com.example.demo.composite.keys.TagNoteConnectionId;

import javax.persistence.*;

@Entity
@Table(name = "tagnoteconnection")
public class TagNoteConnectionEntity {

    @EmbeddedId
    private TagNoteConnectionId tagNoteConnectionId;

    public TagNoteConnectionEntity() {
    }

    public TagNoteConnectionEntity(TagNoteConnectionId tagNoteConnectionId) {
        this.tagNoteConnectionId = tagNoteConnectionId;
    }

    public TagNoteConnectionId getTagNoteConnectionId() {
        return tagNoteConnectionId;
    }

    public void setTagNoteConnectionId(TagNoteConnectionId tagNoteConnectionId) {
        this.tagNoteConnectionId = tagNoteConnectionId;
    }
}
