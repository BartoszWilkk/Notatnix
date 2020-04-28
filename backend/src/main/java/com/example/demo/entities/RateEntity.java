package com.example.demo.entities;

import com.example.demo.composite.keys.RateId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rate")
public class RateEntity {

    @EmbeddedId
    private RateId rateId;

    private int rate;

    public RateId getRateId() {
        return rateId;
    }

    public RateEntity() {
    }

    public RateEntity(RateId rateId, int rate) {
        this.rateId = rateId;
        this.rate = rate;
    }

    public void setRateId(RateId rateId) {
        this.rateId = rateId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
