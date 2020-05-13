package com.example.demo.mappers;

import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.TagEntity;
import com.example.demo.entities.TagNoteConnectionEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.model.NoteModel;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.TagNoteConnectionRepository;
import com.example.demo.repositories.TagRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagNoteConnectionRepository tagNoteConnectionRepository;

    public NoteEntity mapToEntity(NoteModel model){
        NoteEntity entity = new NoteEntity();
        String id = model.getId();
        if (id != null){
            entity.setId(Long.parseLong(id));
        }
        UserEntity userEntity = userRepository.findById(Long.parseLong(model.getUser())).get();
        entity.setUser(userEntity);
        entity.setTitle(model.getTitle());
        entity.setDescription(model.getDescription());
        entity.setUserName(model.getUserName());
        String averageRating = model.getAverageRating();
        if (averageRating != null){
            entity.setAverageRating(Double.parseDouble(averageRating));
        }
        if (model.getFiles() != null) {
            List<String> files = model.getFiles();
            for (String fileId:files) {
                fileRepository.findById(Long.valueOf(fileId)).ifPresent(entity::addFile);
            }
        }
        return entity;
    }

    public NoteModel mapToModel(NoteEntity entity){
        NoteModel model = new NoteModel();
        model.setId(entity.getId().toString());
        model.setUser(entity.getUser().getId().toString());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setUserName(entity.getUserName());
        model.setAverageRating(String.valueOf(entity.getAverageRating()));
        if (entity.getFiles() != null) {
            entity.getFiles().forEach(fileEntity -> model.addFile(fileEntity.getId().toString()));
        }
        List<TagNoteConnectionEntity> tagNoteConnectionEntityList =
                tagNoteConnectionRepository.findAllByTagNoteConnectionId_NoteId(entity.getId());
        List<TagEntity> tagEntityList = new ArrayList<>();
        Long tagId_tmp = null;
        for (TagNoteConnectionEntity tnConnection: tagNoteConnectionEntityList) {
            tagId_tmp = tnConnection.getTagNoteConnectionId().getTagId();
            if (tagRepository.findById(tagId_tmp).isPresent()) {
                tagEntityList.add(tagRepository.findById(tagId_tmp).get());
            }
        }
        tagEntityList.forEach(tag -> model.addTag(tag.getTagName()));
        return model;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public FileRepository getFileRepository() {
        return fileRepository;
    }

    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public TagRepository getTagRepository() {
        return tagRepository;
    }

    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagNoteConnectionRepository getTagNoteConnectionRepository() {
        return tagNoteConnectionRepository;
    }

    public void setTagNoteConnectionRepository(TagNoteConnectionRepository tagNoteConnectionRepository) {
        this.tagNoteConnectionRepository = tagNoteConnectionRepository;
    }
}
