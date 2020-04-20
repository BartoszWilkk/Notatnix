package com.example.demo.services;

import com.example.demo.entities.NoteEntity;
import com.example.demo.mappers.NoteMapper;
import com.example.demo.model.NoteModel;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository repository;

    public NoteModel save(NoteModel model) {
        if (model.getUser()!=null && userRepository.findById(Long.parseLong(model.getUser())).isPresent()){
            if (!repository.existsByTitle(model.getTitle())) {
                NoteEntity entity = mapper.mapToEntity(model);
                NoteEntity entityCreated = repository.save(entity);
                return mapper.mapToModel(entityCreated);
            }
            else return null;
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

    public List<NoteModel> getMyNotes(Long id) {
        if (userRepository.findById(id).isPresent()){
            List<NoteEntity> entityList = repository.findAllByUser(userRepository.findById(id).get());
            return entityList.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
        }
        else return null;
    }
}
