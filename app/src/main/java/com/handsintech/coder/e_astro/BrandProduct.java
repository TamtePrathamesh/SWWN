package com.handsintech.coder.e_astro;

public class BrandProduct {

    private String brand_product_name;

    private String brand_product_logo;



    private String brand_product_price;

    public BrandProduct(String brand_product_name, String brand_product_logo,String brand_product_price) {
        this.brand_product_name = brand_product_name;

        this.brand_product_logo = brand_product_logo;
        this.brand_product_price=brand_product_price;
    }

    public String getBrand_product_name() {
        return brand_product_name;
    }



    public String getBrand_product_logo() {
        return brand_product_logo;
    }

    public String getBrand_product_price() {
        return brand_product_price;
    }
}
