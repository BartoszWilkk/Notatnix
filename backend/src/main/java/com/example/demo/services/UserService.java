package com.example.demo.services;


import com.example.demo.entities.UserEntity;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.Credentials;
import com.example.demo.model.UserModel;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
//            entity.setPassword(bCryptPasswordEncoder.d);
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

        String s = bCryptPasswordEncoder.encode(credentials.getPassword());
        System.out.println(s);
        System.out.println(bCryptPasswordEncoder.matches(credentials.getPassword(), s));

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        if (repository.existsByUsername(username)) {
            UserEntity entity = repository.findUserEntityByUsername(username);
            if (entity.getPassword().equals(password)) {
                return mapper.mapToModel(entity);
            }
            else {
                return null;
            }
        }
        else return null;
    }

//    public UserModel changePassword(String password, Long id) {
//        if (repository.findById(id).isPresent()){
//            NoteEntity entity = repository.findById(id).get();
//            NoteEntity updateEntity = mapper.mapToEntity(model);
//            if (checkTitle && repository.existsByTitle(updateEntity.getTitle())){
//                return null;
//            }
//            else {
//                entity.setTitle(updateEntity.getTitle());
//                entity.setDescription(updateEntity.getDescription());
//                entity.setAverageRating(updateEntity.getAverageRating());
//                repository.save(entity);
//                return mapper.mapToModel(entity);
//            }
//        }
//        else return null;
//    }
}
