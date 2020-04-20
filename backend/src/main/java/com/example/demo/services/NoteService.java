package com.example.demo.services;

import com.example.demo.entities.NoteEntity;
import com.example.demo.mappers.NoteMapper;
import com.example.demo.model.NoteModel;
import com.example.demo.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper mapper;

    @Autowired
    private NoteRepository repository;

    public NoteModel save(NoteModel model) {
        if (!repository.existsByTitle(model.getTitle())) {
            NoteEntity entity = mapper.mapToEntity(model);
            NoteEntity entityCreated = repository.save(entity);
            return mapper.mapToModel(entityCreated);
        }
        else return null;
    }

    public NoteModel edit(NoteModel model, Long id, boolean checkTitle) {
        if (repository.findById(id).isPresent()){
            NoteEntity entity = repository.findById(id).get();
            NoteEntity updateEntity = mapper.mapToEntity(model);
            if (checkTitle && repository.existsByTitle(updateEntity.getTitle())){
                return null;
            }
            else {
                entity.setTitle(updateEntity.getTitle());
                entity.setDescription(updateEntity.getDescription());
                entity.setAverageRating(updateEntity.getAverageRating());
                repository.save(entity);
                return mapper.mapToModel(entity);
            }
        }
        else return null;
    }

    public List<NoteModel> getAll() {
        List<NoteEntity> entityList = repository.findAll();
        List<NoteModel> modelList = new ArrayList<>();
        for (NoteEntity entity:entityList
        ) {
            modelList.add(mapper.mapToModel(entity));
        }
        return modelList;
    }

    public NoteModel getNote(Long id) {
        if (repository.findById(id).isPresent()) {
            return mapper.mapToModel(repository.findById(id).get());
        }
        else return null;
    }

}
