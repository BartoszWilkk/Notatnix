package com.example.demo.mappers;

import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.model.NoteModel;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FileRepository fileRepository;

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
        String averageRating = model.getAverageRating();
        if (averageRating != null){
            entity.setAverageRating(Double.parseDouble(averageRating));
        }
        if (!model.getFiles().isEmpty()) {
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
        model.setAverageRating(""+entity.getAverageRating());
        return model;
    }
}
