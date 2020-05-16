package com.example.demo.repositories;

import com.example.demo.composite.keys.TagNoteConnectionId;
import com.example.demo.entities.TagNoteConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagNoteConnectionRepository extends JpaRepository<TagNoteConnectionEntity, TagNoteConnectionId> {
    List<TagNoteConnectionEntity> findAllByTagNoteConnectionId_NoteId(Long noteId);
    List<TagNoteConnectionEntity> findAllByTagNoteConnectionId_TagId(Long tagId);
    void deleteAllByTagNoteConnectionId_NoteId(Long noteId);
}
