package com.example.demo.controllers;

import com.example.demo.model.FilterParameters;
import com.example.demo.model.NoteModel;
import com.example.demo.model.TagModel;
import com.example.demo.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService service;

    public String hello(String name){
        return String.format("Hello, %s", name);
    }

    @RequestMapping("/save")
    public NoteModel save(@RequestBody NoteModel model){
        return service.save(model);
    }

    @PostMapping("/edit/{id}")
    public NoteModel edit(@RequestBody NoteModel model, @PathVariable Long id) {
        return service.edit(model, id, false);
    }

    @PostMapping("/edit/checkTitle/{id}")
    public NoteModel editCheckTitle(@RequestBody NoteModel model, @PathVariable Long id) {
        return service.edit(model, id, true);
    }

    @RequestMapping("/getAll")
    public List<NoteModel> getAll(){
        return service.getAll();
    }

    @RequestMapping("get/{id}")
    public NoteModel getNote(@PathVariable Long id) {
        return service.getNote(id);
    }

    @RequestMapping("getMyNotes/{id}")
    public List<NoteModel> getMyNotes(@PathVariable Long id){
        return service.getMyNotes(id);
    }

    @PostMapping("/addTag/{id}")
    public NoteModel addTag(@RequestBody TagModel tagModel, @PathVariable Long id) {
        return service.addTag(id, tagModel);
    }

    @PostMapping("/filter")
    public List<NoteModel> filterNotes(@RequestBody FilterParameters filterParameters) {
        return service.filterNotes(filterParameters);
    }

    @RequestMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
