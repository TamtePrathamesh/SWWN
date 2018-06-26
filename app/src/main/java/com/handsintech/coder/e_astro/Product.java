package com.handsintech.coder.e_astro;

public class Product {



    private String name;
    private String lastname;
    private String experience;
    private String details;
    private String type;

    public Product(String name, String lastname, String experience, String details) {
        this.name = name;
        this.lastname = lastname;
        this.experience = experience;
        this.details = details;
    }

    public Product(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getExperience() {
        return experience;
    }

    public String getDetails() {
        return details;
    }
}