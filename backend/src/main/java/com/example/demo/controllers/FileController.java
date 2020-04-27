package com.example.demo.controllers;

import com.example.demo.exceptions.FileStorageException;
import com.example.demo.model.FileModel;
import com.example.demo.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService service;

    @PostMapping("/save/{noteId}")
    public FileModel save(@RequestParam("file") MultipartFile file, @PathVariable Long noteId){
        return service.save(file, noteId);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {
        return service.get(id);
    }

    @RequestMapping("/getByNoteId/{noteId}")
    public List<FileModel> getByNoteId(@PathVariable Long noteId){
        return service.getByNoteId(noteId);
    }

    @GetMapping("/getAll")
    public List<FileModel> getAll() {
        return service.getAll();
    }

}
