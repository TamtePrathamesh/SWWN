package com.handsintech.coder.e_astro.tab_by_brands;

public class Brands {

    private String brand_name;
    private String brand_des;
    private String brand_logo;

    public Brands(String brand_name, String brand_des, String brand_logo) {
        this.brand_name = brand_name;
        this.brand_des = brand_des;
        this.brand_logo = brand_logo;
    }


    public String getBrand_name() {
        return brand_name;
    }

    public String getBrand_des() {
        return brand_des;
    }

    public String getBrand_logo() {
        return brand_logo;
    }
}
