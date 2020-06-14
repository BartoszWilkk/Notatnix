package com.example.demo.services;

import com.example.demo.composite.keys.TagNoteConnectionId;
import com.example.demo.entities.TagNoteConnectionEntity;
import com.example.demo.repositories.TagNoteConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagNoteConnectionService {

    @Autowired
    private TagNoteConnectionRepository repository;

    public TagNoteConnectionEntity save(TagNoteConnectionId tncId) {
        TagNoteConnectionEntity returnValue = null;
        if (repository.findById(tncId).isPresent()) {
            returnValue = repository.findById(tncId).get();
        }
        else {
            TagNoteConnectionEntity tagNoteConnectionEntity = new TagNoteConnectionEntity(tncId);
            returnValue = repository.save(tagNoteConnectionEntity);
        }
        return returnValue;
    }

    @Transactional
    public void delete(Long noteId){

        repository.deleteAllByTagNoteConnectionId_NoteId(noteId);
    }

    public void delete(Long noteId, Long tagId){
        TagNoteConnectionId tagNoteConnectionId = new TagNoteConnectionId(noteId, tagId);
        repository.deleteById(tagNoteConnectionId);
    }

    public void delete(TagNoteConnectionId tncId){
        repository.deleteById(tncId);
    }

    public List<TagNoteConnectionEntity> getConnectionsByTagId(String s_tagId) {
        List<TagNoteConnectionEntity> returnList = null;
        Long tagId = convertToLong(s_tagId);
        if (tagId != null) {
            returnList = repository.findAllByTagNoteConnectionId_TagId(tagId);
        }

        return  returnList;
    }

    public List<TagNoteConnectionEntity> getConnectionsByTagId2(String s_tagId) {
        List<TagNoteConnectionEntity> returnList = null;
        Long tagId = convertToLong(s_tagId);
        if (tagId != null) {
            returnList = repository.findAllByTagNoteConnectionId_TagId(tagId);
        }

        return  returnList;
    }

    public List<TagNoteConnectionEntity> getConnectionsByNoteId(Long noteId) {
        List<TagNoteConnectionEntity> returnList = null;
        if (noteId != null) {
            returnList = repository.findAllByTagNoteConnectionId_NoteId(noteId);
        }
        return  returnList;
    }

    private Long convertToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        else return null;
    }
}
