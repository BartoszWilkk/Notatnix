package com.example.demo.repositories;

import com.example.demo.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
    UserEntity findUserEntityByUsername(String username);
    List<UserEntity> findAllByRole(String role);
}
