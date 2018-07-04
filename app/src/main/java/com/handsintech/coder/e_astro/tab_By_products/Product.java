package com.handsintech.coder.e_astro.tab_By_products;

public class Product {


    private String id;
    private String product_name;
    private String sku;
    private String product_des;
    private String product_logo;

    public Product(String id, String product_name, String sku, String product_des, String product_logo) {
        this.id = id;
        this.product_name = product_name;
        this.sku = sku;
        this.product_des = product_des;
        this.product_logo = product_logo;


    }

    public Product(String id, String product_name, String sku, String product_logo) {
        this.id = id;
        this.product_name = product_name;
        this.sku = sku;
        this.product_logo = product_logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProduct_des() {
        return product_des;
    }

    public void setProduct_des(String product_des) {
        this.product_des = product_des;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }
}
