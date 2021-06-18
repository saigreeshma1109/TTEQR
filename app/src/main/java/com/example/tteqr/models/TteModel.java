package com.example.tteqr.models;

import java.util.List;

public class TteModel {
    private  String name;
    private int age;
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getCurrent() {
        return current;
    }

    public void setCurrent(List<String> current) {
        this.current = current;
    }

    public TteModel() {
    }

    private List <String> current;
}
