package com.example.demo.mappers;

import com.example.demo.entities.NoteEntity;
import com.example.demo.model.NoteModel;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteEntity mapToEntity(NoteModel model){
        NoteEntity entity = new NoteEntity();
        String id = model.getId();
        if (id != null){
            entity.setId(Long.parseLong(id));
        }
        entity.setTitle(model.getTitle());
        entity.setDescription(model.getDescription());
        String averageRating = model.getAverageRating();
        if (averageRating != null){
            entity.setAverageRating(Double.parseDouble(averageRating));
        }
        return entity;
    }

    public NoteModel mapToModel(NoteEntity entity){
        NoteModel model = new NoteModel();
        model.setId(entity.getId().toString());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setAverageRating(""+entity.getAverageRating());
        return model;
    }
}
