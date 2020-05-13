package com.example.demo.repositories;

import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    boolean existsByTitle(String title);
    List<NoteEntity> findAllByUser(UserEntity entity);
    List<NoteEntity> findAllByTitleContaining(String title);
    List<NoteEntity> findAllByDescriptionContaining(String description);
    List<NoteEntity> findAllByUserNameContaining(String userName);
    List<NoteEntity> findAllByTitleContainingAndDescriptionContainingAndUserNameContaining(String title, String description, String userName);
}
