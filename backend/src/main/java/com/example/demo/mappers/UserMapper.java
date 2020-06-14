package com.example.demo.mappers;

import com.example.demo.entities.UserEntity;
import com.example.demo.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity mapToEntity(UserModel model){
        UserEntity entity = new UserEntity();
        String id = model.getId();
        if (id != null){
            entity.setId(Long.parseLong(id));
        }
        entity.setUsername(model.getUsername());
        entity.setEmailAddress(model.getEmailAddress());
        entity.setPassword(model.getPassword());
        entity.setRole(model.getRole());
        return entity;
    }

    public UserModel mapToModel(UserEntity entity){
        UserModel model = new UserModel();
        model.setId(entity.getId().toString());
        model.setUsername(entity.getUsername());
        model.setEmailAddress(entity.getEmailAddress());
        model.setPassword(entity.getPassword());
        model.setRole(entity.getRole());
        return model;
    }


}
