package com.example.demo.services;

import com.example.demo.composite.keys.RateId;
import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.RateEntity;
import com.example.demo.mappers.RateMapper;
import com.example.demo.model.RateModel;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.RateRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private RateMapper mapper;

    public RateModel getRate(RateModel model) {
        Long userId = convertToLong(model.getUserId());
        Long noteId = convertToLong(model.getNoteId());
        if (userId != null && noteId != null) {
            RateId rateId = new RateId(userId, noteId);
            if (repository.findById(rateId).isPresent()) {
                return mapper.mapToModel(repository.findById(rateId).get());
            }
            else return null;
        }
        else return null;
    }

    public RateModel saveRate(RateModel model) {
        RateModel returnValue = null;
        Long userId = convertToLong(model.getUserId());
        Long noteId = convertToLong(model.getNoteId());
        if (userId != null && noteId != null) {
            if (userRepository.existsById(userId) && noteRepository.existsById(noteId)) {
                RateId rateId = getKey(model);
                if (!repository.existsById(rateId)) {
                    RateEntity entitySaved = repository.save(new RateEntity(rateId, Integer.parseInt(model.getRate())));
                    if (entitySaved != null) {
                        updateNoteRating(noteId);
                        returnValue = mapper.mapToModel(entitySaved);
                    }
                }
            }
        }
        return returnValue;
    }

    public RateModel editRate(RateModel model) {
        RateModel returnValue = null;
        RateId rateId = getKey(model);
        if (rateId != null) {
            if (repository.findById(rateId).isPresent()) {
                RateEntity rateEntity = repository.findById(rateId).get();
                rateEntity.setRate(Integer.parseInt(model.getRate()));
                RateEntity rateEntitySaved = repository.save(rateEntity);
                updateNoteRating(convertToLong(model.getNoteId()));
                returnValue = mapper.mapToModel(rateEntitySaved);
            }
        }
        return returnValue;
    }

    public boolean deleteRate(RateModel model) {
        boolean returnValue = false;
        RateId rateId = getKey(model);
        if (rateId != null) {
            if (repository.existsById(rateId)) {
                repository.deleteById(rateId);
                returnValue = true;
            }
            else {
                System.out.println("There isn't such a Rate");
            }
        }
        return returnValue;
    }

    private void updateNoteRating(Long noteId) {
        if (noteRepository.findById(noteId).isPresent()) {
            NoteEntity noteEntity = noteRepository.findById(noteId).get();
            List<RateEntity> rateEntityList = repository.findAllByRateId_NoteId(noteId);
            AtomicReference<Double> rate = new AtomicReference<>(0.0);
            rateEntityList.forEach(rateEntity -> rate.updateAndGet(v -> v + rateEntity.getRate()));
            double avgRate = rate.get() / rateEntityList.size();
            noteEntity.setAverageRating(avgRate);
            noteRepository.save(noteEntity);
        }
        else System.out.println("I don't know how but you have error in method: " +
                "void addRateToNote(Long noteId, int rate) in " +
                "class RateService");
    }

    private RateId getKey(RateModel model) {
        Long userId = convertToLong(model.getUserId());
        Long noteId = convertToLong(model.getNoteId());
        if (userId != null && noteId != null) {
            return new RateId(userId, noteId);
        }
        else return null;
    }

    private Long convertToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        else return null;
    }

    public RateRepository getRepository() {
        return repository;
    }

    public void setRepository(RateRepository repository) {
        this.repository = repository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public RateMapper getMapper() {
        return mapper;
    }

    public void setMapper(RateMapper mapper) {
        this.mapper = mapper;
    }
}
