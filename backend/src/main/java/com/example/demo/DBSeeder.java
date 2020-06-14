package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.UserModel;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBSeeder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUsername("admin");
        userModel.setPassword("admin");
        userModel.setEmailAddress("admin@wp.pl");
        userModel.setRole("admin");
        userService.save(userModel);
//        deleteData();
    }

    private void deleteData(){
        noteRepository.deleteAll();
        noteRepository.flush();
    }
}
