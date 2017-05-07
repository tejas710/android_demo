package com.example.jay3165.garglibrary;

/**
 * Created by jay3165 on 2/28/2017.
 */

public class subject {
   public String cat_id;
    public String sub_cat_id;
    public String sub_cat_name;
    public String main_cat_id;

    public subject(String cat_id, String sub_cat_id, String sub_cat_name, String main_cat_id) {
        this.cat_id = cat_id;
        this.sub_cat_id = sub_cat_id;
        this.sub_cat_name = sub_cat_name;
        this.main_cat_id = main_cat_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getMain_cat_id() {
        return main_cat_id;
    }

    public void setMain_cat_id(String main_cat_id) {
        this.main_cat_id = main_cat_id;
    }
}
