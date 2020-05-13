package com.example.demo.controllers;

import com.example.demo.entities.TagEntity;
import com.example.demo.model.TagModel;
import com.example.demo.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @RequestMapping("/getAll")
    public List<TagModel> getAll() {
        return tagService.getAll();
    }

    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
