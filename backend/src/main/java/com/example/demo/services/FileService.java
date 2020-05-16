package com.example.demo.services;

import com.example.demo.entities.FileEntity;
import com.example.demo.entities.NoteEntity;
import com.example.demo.exceptions.FileStorageException;
import com.example.demo.mappers.FileMapper;
import com.example.demo.model.FileModel;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    FileRepository repository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    FileMapper mapper;

    public FileModel save(MultipartFile fileModel, Long noteId) {
        String fileName = StringUtils.cleanPath(fileModel.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            if (noteRepository.findById(noteId).isPresent()) {
                NoteEntity noteEntity = noteRepository.findById(noteId).get();
                FileEntity fileEntity = new FileEntity();
                fileEntity.setName(fileName);
                fileEntity.setTypeOfFile(fileModel.getContentType());
                fileEntity.setData(fileModel.getBytes());
                fileEntity.setNoteEntity(noteEntity);
                FileEntity fileEntityCreated = repository.save(fileEntity);
                repository.flush();
                noteEntity.addFile(fileEntity);
                noteRepository.save(noteEntity);
                return mapper.mapToModel(fileEntityCreated);
            }
            else {
                return null;
            }
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void delete(Long noteId) {
        repository.deleteAllByNoteEntityId(noteId);
    }

    public ResponseEntity<Resource> get(Long id) {
        if (repository.findById(id).isPresent()){
            FileEntity fileEntity = repository.findById(id).get();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileEntity.getTypeOfFile()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                    .body(new ByteArrayResource(fileEntity.getData()));
        }
        else {
            return null;
        }
    }

    public List<FileModel> getByNoteId(Long noteId) {
        if (noteRepository.existsById(noteId)) {
            List<FileEntity> fileEntities = repository.findAllByNoteEntityId(noteId);
            List<FileModel> returnList = fileEntities.stream()
                    .map(file -> this.mapper.mapToModel(file))
                    .collect(Collectors.toList());
            return returnList;
        }
        else return null;
    }

    public List<FileModel> getAll() {
        return repository.findAll().stream().map(fileEntity -> mapper.mapToModel(fileEntity)).collect(Collectors.toList());
    }
}
