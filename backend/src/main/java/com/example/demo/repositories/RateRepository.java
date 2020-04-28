package com.example.demo.repositories;

import com.example.demo.composite.keys.RateId;
import com.example.demo.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<RateEntity, RateId> {
    List<RateEntity> findAllByRateIdNoteIdContains(Long rateId_noteId);
    List<RateEntity> findAllByRateId_NoteId(Long rateId_noteId);
}
