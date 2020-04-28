package com.example.demo.composite.keys;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RateId implements Serializable {

    @Column
    private Long userId;

    @Column
    private Long noteId;

    public RateId() {
    }

    public RateId(Long userId, Long noteId) {
        this.userId = userId;
        this.noteId = noteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateId that = (RateId) o;
        return userId.equals(that.userId) &&
                noteId.equals(that.noteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, noteId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }
}
