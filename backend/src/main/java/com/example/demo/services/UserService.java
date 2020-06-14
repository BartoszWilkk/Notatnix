package com.example.demo.services;


import com.example.demo.entities.UserEntity;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Credentials;
import com.example.demo.model.UserModel;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserMapper mapper;

    @Autowired
    UserRepository repository;

    public UserModel save(UserModel model) {
        if (!repository.existsByUsername(model.getUsername())){
            UserEntity entity = mapper.mapToEntity(model);
            entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
            UserEntity entityCreated = repository.save(entity);
            return mapper.mapToModel(entityCreated);
        }
        else return null;
    }

    public UserModel getUser(Long id) {
        if (repository.findById(id).isPresent()) {
            return mapper.mapToModel(repository.findById(id).get());
        }
        else return null;
    }

    public UserModel login(Credentials credentials) {


        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (repository.existsByUsername(username)) {
            UserEntity entity = repository.findUserEntityByUsername(username);
            if (bCryptPasswordEncoder.matches(password, entity.getPassword())) {
                if (entity.getRole().equals("banned")) {
                    return null;
                }
                return mapper.mapToModel(entity);
            }
            else {
                return null;
            }
        }
        else return null;
    }

    public List<UserModel> getAllActiveUsers() {
        List<UserEntity> entityList = repository.findAllByRole("user");
        return entityList.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
    }

    public List<UserModel> getAllBannedUsers() {
        List<UserEntity> entityList = repository.findAllByRole("banned");
        return entityList.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
    }

    public UserModel banUser(Long id) {
        if (repository.findById(id).isPresent()) {
            UserEntity entity = repository.findById(id).get();
            entity.setRole("banned");
            UserEntity returnEntity = repository.save(entity);
            return mapper.mapToModel(returnEntity);
        }
        else return null;
    }
}
