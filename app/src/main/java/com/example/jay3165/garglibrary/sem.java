package com.example.jay3165.garglibrary;

/**
 * Created by jay3165 on 2/28/2017.
 */

public class sem {
    public String main_sub_id;
    public String main_sub_name;
    public String cat_id;

    public sem(String main_sub_id, String main_sub_name, String cat_id) {
        this.main_sub_id = main_sub_id;
        this.main_sub_name = main_sub_name;
        this.cat_id = cat_id;
    }

    public String getMain_sub_id() {
        return main_sub_id;
    }

    public void setMain_sub_id(String main_sub_id) {
        this.main_sub_id = main_sub_id;
    }

    public String getMain_sub_name() {
        return main_sub_name;
    }

    public void setMain_sub_name(String main_sub_name) {
        this.main_sub_name = main_sub_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
