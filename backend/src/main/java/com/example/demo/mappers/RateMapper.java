package com.example.demo.mappers;

import com.example.demo.composite.keys.RateId;
import com.example.demo.entities.RateEntity;
import com.example.demo.model.RateModel;
import org.springframework.stereotype.Component;

@Component
public class RateMapper {

    public RateEntity mapToEntity(RateModel model) {
        RateId rateId = new RateId(convertToLong(model.getUserId()), convertToLong(model.getNoteId()));
        return new RateEntity(rateId, Integer.parseInt(model.getRate()));
    }

    public RateModel mapToModel(RateEntity entity) {
        return new RateModel(entity.getRateId().getUserId().toString(),
                entity.getRateId().getNoteId().toString(), String.valueOf(entity.getRate()));
    }

    private Long convertToLong(String number) {
        if (number != null) {
            return Long.parseLong(number);
        }
        else return null;
    }
}
