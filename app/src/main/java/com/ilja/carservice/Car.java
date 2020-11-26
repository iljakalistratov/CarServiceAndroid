package com.ilja.carservice;

import java.io.Serializable;

public class Car implements Serializable {

    int id;
    String model;
    String brand;
    String leistung;
    String baujahr;
    String motor;
    String verbrauch;

    public Car() {
    }

    public Car(int id, String model, String brand, String leistung, String baujahr, String motor, String verbrauch) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.leistung = leistung;
        this.baujahr = baujahr;
        this.motor = motor;
        this.verbrauch = verbrauch;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLeistung() {
        return leistung;
    }

    public void setLeistung(String leistung) {
        this.leistung = leistung;
    }

    public String getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(String baujahr) {
        this.baujahr = baujahr;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getVerbrauch() {
        return verbrauch;
    }

    public void setVerbrauch(String verbrauch) {
        this.verbrauch = verbrauch;
    }
}
