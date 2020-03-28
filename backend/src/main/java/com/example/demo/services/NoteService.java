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

    public NoteModel save(NoteModel model){
        NoteEntity entity = mapper.mapToEntity(model);
        NoteEntity entityCreated = repository.save(entity);
        return mapper.mapToModel(entityCreated);
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

}
