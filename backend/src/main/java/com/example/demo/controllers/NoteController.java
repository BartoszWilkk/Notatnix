package com.example.demo.controllers;

import com.example.demo.model.NoteModel;
import com.example.demo.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService service;

    @RequestMapping("/save")
    public NoteModel save(@RequestBody NoteModel model){
        return service.save(model);
    }

    @RequestMapping("/getAll")
    public List<NoteModel> getAll(){
        return service.getAll();
    }

}
