package com.example.demo.repositories;

import com.example.demo.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    boolean existsByTitle(String title);
}
