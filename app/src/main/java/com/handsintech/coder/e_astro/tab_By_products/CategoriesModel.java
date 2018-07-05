package com.handsintech.coder.e_astro.tab_By_products;

public class CategoriesModel {


    private String category_id;
    private String categriesname;

    public CategoriesModel(String category_id, String categriesname) {
        this.category_id = category_id;
        this.categriesname = categriesname;
    }


//
    public String getCategory_id() {
        return category_id;
    }

    public String getCategriesname() {
        return categriesname;
    }
}

