package com.example.demo.repositories;

import com.example.demo.composite.keys.RateId;
import com.example.demo.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<RateEntity, RateId> {
}
