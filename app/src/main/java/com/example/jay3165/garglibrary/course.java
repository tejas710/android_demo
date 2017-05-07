package com.example.jay3165.garglibrary;

/**
 * Created by jay3165 on 2/24/2017.
 */

public class course {

    public String main_cat_id;
    public String main_cat_name;

    public course(String main_cat_id, String main_cat_name) {
        this.main_cat_id = main_cat_id;
        this.main_cat_name = main_cat_name;
    }

    public String getMain_cat_id() {
        return main_cat_id;
    }

    public void setMain_cat_id(String main_cat_id) {
        this.main_cat_id = main_cat_id;
    }

    public String getMain_cat_name() {
        return main_cat_name;
    }

    public void setMain_cat_name(String main_cat_name) {
        this.main_cat_name = main_cat_name;
    }
}
