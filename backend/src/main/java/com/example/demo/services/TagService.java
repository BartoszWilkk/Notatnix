package com.example.demo.services;

import com.example.demo.entities.TagEntity;
import com.example.demo.mappers.TagMapper;
import com.example.demo.model.TagModel;
import com.example.demo.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository repository;

    @Autowired
    private TagMapper mapper;

    public List<TagModel> getAll() {
        List<TagEntity> tagEntityList = repository.findAll();
        List<TagModel> tagModelList = new ArrayList<>();
        tagEntityList.forEach(tag -> tagModelList.add(mapper.mapToModel(tag)));
        return tagModelList;
    }

    public TagEntity saveTagEntity(TagModel model) {
        TagEntity returnValue = null;
        String nameOfTag = model.getTagName();
        boolean tagExist = repository.existsByTagName(nameOfTag);
        if (tagExist){
            returnValue = repository.findByTagName(nameOfTag);
        } else {
            TagEntity tagEntitySaved = repository.save(mapper.mapToEntity(model));
            if (tagEntitySaved != null){
                returnValue = tagEntitySaved;
            }
        }
        return returnValue;
    }

    public TagEntity getById(String s_id) {

        TagEntity returnValue = null;
        Long id = convertToLong(s_id);
        if (repository.findById(id).isPresent()) {
            returnValue = repository.findById(id).get();
        }

        return returnValue;
    }

    public TagEntity getByTagName(String tagName) {

        TagEntity returnValue = null;
        if (repository.existsByTagName(tagName)) {
            returnValue = repository.findByTagName(tagName);
        }

        return returnValue;
    }

    public void deleteTag(Long id){
        repository.deleteById(id);
    }

    public TagEntity saveTagEntity2(TagModel model) {
        TagEntity returnValue = null;
        Long tagId = convertToLong(model.getId());
        if (tagId != null) {
            if (repository.findById(tagId).isEmpty() && !repository.existsByTagName(model.getTagName())) {
                TagEntity tagEntitySaved = repository.save(mapper.mapToEntity(model));
                if (tagEntitySaved != null) {
                    returnValue = tagEntitySaved;
                }
            }
            else {
                returnValue = repository.findById(tagId).get();
            }
        }
        else {
            TagEntity tagEntitySaved = repository.save(mapper.mapToEntity(model));
            if (tagEntitySaved != null) {
                returnValue = tagEntitySaved;
            }
        }
        return returnValue;
    }

    private Long convertToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        else return null;
    }
}
