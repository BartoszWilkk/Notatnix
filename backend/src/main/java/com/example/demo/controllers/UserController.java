package com.example.demo.controllers;

import com.example.demo.model.Credentials;
import com.example.demo.model.UserModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @RequestMapping("/save")
    public UserModel save(@RequestBody UserModel model) {
        return service.save(model);
    }

    @RequestMapping("/get/{id}")
    public UserModel get(@PathVariable Long id) {
        return service.getUser(id);
    }

    @RequestMapping("/login")
    public UserModel login(@RequestBody Credentials credentials) {
        return service.login(credentials);
    }
}
