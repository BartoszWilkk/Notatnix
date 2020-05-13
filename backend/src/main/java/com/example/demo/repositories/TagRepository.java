package com.example.demo.repositories;

import com.example.demo.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    boolean existsByTagName(String tagName);
    TagEntity findByTagName(String tagName);
}
