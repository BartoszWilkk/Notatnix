package com.example.demo.controllers;

import com.example.demo.model.RateIdModel;
import com.example.demo.model.RateModel;
import com.example.demo.services.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rate")
public class RateController {

    @Autowired
    private RateService service;

    @PostMapping("/get")
    public RateModel get(@RequestBody RateModel model) {
        return service.getRate(model);
    }

    @PostMapping("/getMyRate")
    public String getMyRate(@RequestBody RateIdModel rateIdModel) {
        return service.getMyRate(rateIdModel);
    }

    @PostMapping("/save")
    public String save(@RequestBody RateModel model) {
        return service.saveRate(model);
    }

    @PostMapping("/edit")
    public RateModel edit(@RequestBody RateModel model) {
        return service.editRate(model);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody RateModel model) {
        return service.deleteRate(model);
    }
}
