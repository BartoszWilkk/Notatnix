package com.example.demo.repositories;


import com.example.demo.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByNoteEntityId(Long noteEntity_id);
    List<FileEntity> findAllByNoteEntityId(Long noteEntity_id);
    void deleteAllByNoteEntityId(Long noteId);
}
