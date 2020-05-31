package com.example.demo;

import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DBSeeder implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NoteRepository noteRepository;

    @Override
    public void run(String... args) throws Exception {
//        deleteData();
    }

    private void deleteData(){
        noteRepository.deleteAll();
        noteRepository.flush();
    }
}
