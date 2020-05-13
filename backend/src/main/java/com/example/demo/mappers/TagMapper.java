package com.example.demo.mappers;

import com.example.demo.entities.TagEntity;
import com.example.demo.model.TagModel;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public TagEntity mapToEntity(TagModel model) {
        TagEntity entity = new TagEntity();
        Long tagId = convertToLong(model.getId());
        if (tagId != null) {
            entity.setId(tagId);
        }
        entity.setTagName(model.getTagName());
        return entity;
    }

    public TagModel mapToModel(TagEntity entity) {
        TagModel model = new TagModel();
        model.setId(entity.getId().toString());
        model.setTagName(entity.getTagName());
        return model;
    }

    private Long convertToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        else return null;
    }
}
