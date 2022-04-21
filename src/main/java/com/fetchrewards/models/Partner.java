package com.fetchrewards.models;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Partner {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String payer;
    private int points;

    public Partner() {
    }

    public Partner(String payer, int points) {
        this.payer = payer;
        this.points = points;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
