package com.example.demo.services;

import com.example.demo.composite.keys.RateId;
import com.example.demo.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RateService {

    @Autowired
    private RateRepository repository;

    public int getRate(String s_userId, String s_noteId) {
        AtomicInteger rate = new AtomicInteger(-1);
        Long userId = convertToLong(s_userId);
        Long noteId = convertToLong(s_noteId);
        if (userId != null && noteId != null) {
            RateId rateId = new RateId(userId, noteId);
            repository.findById(rateId).ifPresent(rateEntity -> rate.set(rateEntity.getRate()));
            return rate.get();
        }
        else return -1;
    }

    private Long convertToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        else return null;
    }
}
