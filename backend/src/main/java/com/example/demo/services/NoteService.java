package com.example.demo.services;

import com.example.demo.composite.keys.TagNoteConnectionId;
import com.example.demo.entities.NoteEntity;
import com.example.demo.entities.TagEntity;
import com.example.demo.entities.TagNoteConnectionEntity;
import com.example.demo.mappers.NoteMapper;
import com.example.demo.mappers.TagMapper;
import com.example.demo.model.FilterParameters;
import com.example.demo.model.NoteModel;
import com.example.demo.model.TagModel;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteMapper mapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository repository;

    @Autowired
    private TagService tagService;

    @Autowired
    private RateService rateService;

    @Autowired
    private FileService fileService;

    @Autowired
    private TagNoteConnectionService tagNoteConnectionService;

    public NoteModel save(NoteModel model) {
        if (model.getUser()!=null && userRepository.findById(Long.parseLong(model.getUser())).isPresent()){
            if (!repository.existsByTitle(model.getTitle())) {
                NoteEntity entity = mapper.mapToEntity(model);
                entity.setUserName(userRepository.findById(Long.parseLong(model.getUser())).get().getUsername());
                NoteEntity entityCreated = repository.save(entity);
                if (entityCreated != null) {
                    List<String> tags = model.getTags();
                    tags.forEach(tag -> addTag(entityCreated.getId(), new TagModel(tag)));
                    return mapper.mapToModel(entityCreated);
                } else {
                    return null;
                }
            }
            else return null;
        }
        else return null;
    }

    public NoteModel edit(NoteModel model, Long id, boolean checkTitle) {
        if (repository.findById(id).isPresent()){
            NoteEntity entity = repository.findById(id).get();
            NoteEntity updateEntity = mapper.mapToEntity(model);
            if (checkTitle && repository.existsByTitle(updateEntity.getTitle())){
                return null;
            }
            else {
                entity.setTitle(updateEntity.getTitle());
                entity.setDescription(updateEntity.getDescription());
                entity.setAverageRating(updateEntity.getAverageRating());
                repository.save(entity);
                updateTags(entity.getId(), model.getTags());
                return mapper.mapToModel(entity);
            }
        }
        else return null;
    }

    private void updateTags(Long noteId, List<String> tags){
        List<TagNoteConnectionId> listOfNewTagsIds = new ArrayList<>();
        for (String tag:tags) {
            TagEntity tagEntityExisted = tagService.getByTagName(tag);
            if (tagEntityExisted == null) {
                listOfNewTagsIds.add(new TagNoteConnectionId(noteId, tagService.saveTagEntity(new TagModel(tag)).getId()));
            } else {
                listOfNewTagsIds.add(new TagNoteConnectionId(noteId, tagEntityExisted.getId()));
            }
        }
        List<TagNoteConnectionId> listOfOldTagsIds = new ArrayList<>();
        List<TagNoteConnectionEntity> tagNoteConnectionEntityList = tagNoteConnectionService.getConnectionsByNoteId(noteId);
        for (TagNoteConnectionEntity entity:tagNoteConnectionEntityList) {
            listOfOldTagsIds.add(entity.getTagNoteConnectionId());
        }
        List<TagNoteConnectionId> listOfToDeleteTagsIds = listOfOldTagsIds;
        listOfToDeleteTagsIds.removeAll(listOfNewTagsIds);

        List<TagNoteConnectionId> listOfToAddTagsIds = listOfNewTagsIds;
        listOfToAddTagsIds.removeAll(listOfOldTagsIds);

        deleteTagNoteConnections(listOfToDeleteTagsIds);
        addTagNoteConnections(listOfToAddTagsIds);
    }

    private void addTagNoteConnections(List<TagNoteConnectionId> list){
        list.forEach(element -> tagNoteConnectionService.save(element));
    }

    private void deleteTagNoteConnections(List<TagNoteConnectionId> list){
        list.forEach(element -> tagNoteConnectionService.delete(element));
    }

    public List<NoteModel> getAll() {
        List<NoteEntity> entityList = repository.findAll();
        List<NoteModel> modelList = new ArrayList<>();
        for (NoteEntity entity:entityList
        ) {
            modelList.add(mapper.mapToModel(entity));
        }
        return modelList;
    }

    public NoteModel getNote(Long id) {
        if (repository.findById(id).isPresent()) {
            return mapper.mapToModel(repository.findById(id).get());
        }
        else return null;
    }

    public List<NoteModel> getMyNotes(Long id) {
        if (userRepository.findById(id).isPresent()){
            List<NoteEntity> entityList = repository.findAllByUser(userRepository.findById(id).get());
            return entityList.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
        }
        else return null;
    }

    public NoteModel addTag(Long id, TagModel tagModel) {
        NoteModel returnValue = null;
        NoteEntity noteEntity = null;
        if (repository.findById(id).isPresent()) {
            noteEntity = repository.findById(id).get();
        }
        if (noteEntity != null) {

            TagEntity tagEntity = tagService.saveTagEntity(tagModel);
            if (tagEntity != null) {
                TagNoteConnectionId tncId = new TagNoteConnectionId(noteEntity.getId(), tagEntity.getId());
                TagNoteConnectionEntity tagNoteConnectionEntity = tagNoteConnectionService.save(tncId);
                if (tagNoteConnectionEntity != null) {
                    returnValue = mapper.mapToModel(noteEntity);
                }
            }
        }
        return returnValue;
    }

    public List<NoteModel> filterNotes(FilterParameters filterParameters) {
        List<NoteModel> returnList = null;
        String title = null;
        String description = null;
        String userName = null;
        List<String> tags = new ArrayList<>();
        boolean isAggregate = false;
        if(filterParameters.getTitle() != null) {
            title = filterParameters.getTitle();
        }
        if(filterParameters.getDescription() != null) {
            description = filterParameters.getDescription();
        }
        if(filterParameters.getUserName() != null) {
            userName = filterParameters.getUserName();
        }
        if(filterParameters.getTags().size() > 0) {
            tags = filterParameters.getTags();
        }
        if(filterParameters.getIsAggregate() != null) {
            isAggregate = Boolean.parseBoolean(filterParameters.getIsAggregate());
        }

        if (isAggregate) {
            returnList = filterNotesByAllParameters(title, description, userName, tags);
        }
//        if (title != null) {
//            returnList = filterNotesByTitle(title);
//        }
//
//        if (description != null) {
//            returnList = filterNotesByTitle(description);
//        }
//
//        if (userName != null) {
//            returnList = filterNotesByTitle(userName);
//        }
//
//        if (tag != null) {
//            returnList = filterNotesByTag(tag);
//        }


        return returnList;
    }

    public List<NoteModel> filterNotesByTitle(String title) {
        List<NoteModel> returnList = null;
        List<NoteEntity> entityListSearched = repository.findAllByTitleContaining(title);
        if (entityListSearched != null) {
            returnList = entityListSearched.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
        }

        return returnList;
    }

    public List<NoteModel> filterNotesByDescription(String description) {
        List<NoteModel> returnList = null;
        List<NoteEntity> entityListSearched = repository.findAllByDescriptionContaining(description);
        if (entityListSearched != null) {
            returnList = entityListSearched.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
        }

        return returnList;
    }

    public List<NoteModel> filterNotesByUserName(String userName) {
        List<NoteModel> returnList = null;
        List<NoteEntity> entityListSearched = repository.findAllByUserNameContaining(userName);
        if (entityListSearched != null) {
            returnList = entityListSearched.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
        }

        return returnList;
    }

    public List<NoteModel> filterNotesByAllParameters(String title, String description, String userName, List<String> tags) {
        List<NoteModel> returnList = null;
        List<NoteEntity> entityListSearched =
                repository.findAllByTitleContainingAndDescriptionContainingAndUserNameContaining(
                        title, description, userName);
        if (entityListSearched != null) {
            List<NoteModel> modelListSearched =
                    entityListSearched.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
            if (modelListSearched.size() > 0) {
                if (tags.size() > 0) {
                    returnList = new ArrayList<>();
                    boolean isContaining = true;
                    for (NoteModel model:modelListSearched) {
                        isContaining = true;
                        List<String> listOfTags =  model.getTags();
                        for (String tagName:tags) {
                            if (!listOfTags.contains(tagName)) {
                                isContaining = false;
                                break;
                            }
                        }
                        if (isContaining) {
                            returnList.add(model);
                        }
                    }
                }
                else {
                    returnList = modelListSearched;
                }

            }
        }

        return returnList;
    }








    // parametrem jest id tagu !!
    public List<NoteModel> filterNotesByTag(String tag) {
        List<NoteModel> returnList = null;
        List<TagNoteConnectionEntity> tagNoteConnectionEntityList = tagNoteConnectionService.getConnectionsByTagId(tag);
        if (tagNoteConnectionEntityList.size() > 0) {
            List<NoteEntity> entityListSearched = new ArrayList<>();
            Long noteId;
            for (TagNoteConnectionEntity entity:tagNoteConnectionEntityList) {
                noteId = entity.getTagNoteConnectionId().getNoteId();
                if (repository.findById(noteId).isPresent()) {
                    entityListSearched.add(repository.findById(noteId).get());
                }
            }
            returnList = entityListSearched.stream().map(entity -> mapper.mapToModel(entity)).collect(Collectors.toList());
        }

        return returnList;
    }










    private Long convertToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        else return null;
    }

    public NoteMapper getMapper() {
        return mapper;
    }

    public void setMapper(NoteMapper mapper) {
        this.mapper = mapper;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public NoteRepository getRepository() {
        return repository;
    }

    public void setRepository(NoteRepository repository) {
        this.repository = repository;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public TagNoteConnectionService getTagNoteConnectionService() {
        return tagNoteConnectionService;
    }

    public void setTagNoteConnectionService(TagNoteConnectionService tagNoteConnectionService) {
        this.tagNoteConnectionService = tagNoteConnectionService;
    }

    public void delete(Long id) {
        if (id != null) {
            if (repository.existsById(id)) {
                tagNoteConnectionService.delete(id);
                rateService.delete(id);
                fileService.delete(id);
                repository.deleteById(id);
            }
        }
    }
}
