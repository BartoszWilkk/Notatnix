package com.example.demo.mappers;

import com.example.demo.entities.FileEntity;
import com.example.demo.entities.NoteEntity;
import com.example.demo.model.FileModel;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public FileModel mapToModel(FileEntity entity){
        FileModel model = new FileModel();
        model.setId(entity.getId().toString());
        model.setName(entity.getName());
        model.setTypeOfFile(entity.getTypeOfFile());
        model.setNoteEntity(
                        entity.getNoteEntity() == null ? null : entity.getNoteEntity().getId().toString()
        );
        return model;
    }

    public FileEntity mapToEntity(FileModel model, NoteEntity noteEntity) {
        FileEntity entity = new FileEntity();
        String id = model.getId();
        if (id != null){
            entity.setId(Long.parseLong(id));
        }
        entity.setName(model.getName());
        entity.setTypeOfFile(model.getTypeOfFile());
        entity.setNoteEntity(noteEntity);
        return entity;
    }
}
