package com.example.jay3165.garglibrary;

/**
 * Created by jay3165 on 3/7/2017.
 */

public class cart {
    public String p_id;
    public String p_name;
    public String p_price;
    public String p_brand_name;
    public String main_cat_id;
    public String main_sub_id;
    public String sub_cat_id;
    public String p_specification;
    public String p_image;
    public String p_discount;
    public String p_cat_id;
    public String p_status;
    public String p_stock;

    public cart(String p_id, String p_name, String p_price, String p_brand_name, String main_cat_id, String main_sub_id, String sub_cat_id, String p_specification, String p_image, String p_discount, String p_cat_id, String p_status, String p_stock) {
        this.p_id = p_id;
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_brand_name = p_brand_name;
        this.main_cat_id = main_cat_id;
        this.main_sub_id = main_sub_id;
        this.sub_cat_id = sub_cat_id;
        this.p_specification = p_specification;
        this.p_image = p_image;
        this.p_discount = p_discount;
        this.p_cat_id = p_cat_id;
        this.p_status = p_status;
        this.p_stock = p_stock;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_brand_name() {
        return p_brand_name;
    }

    public void setP_brand_name(String p_brand_name) {
        this.p_brand_name = p_brand_name;
    }

    public String getMain_cat_id() {
        return main_cat_id;
    }

    public void setMain_cat_id(String main_cat_id) {
        this.main_cat_id = main_cat_id;
    }

    public String getMain_sub_id() {
        return main_sub_id;
    }

    public void setMain_sub_id(String main_sub_id) {
        this.main_sub_id = main_sub_id;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getP_specification() {
        return p_specification;
    }

    public void setP_specification(String p_specification) {
        this.p_specification = p_specification;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getP_discount() {
        return p_discount;
    }

    public void setP_discount(String p_discount) {
        this.p_discount = p_discount;
    }

    public String getP_cat_id() {
        return p_cat_id;
    }

    public void setP_cat_id(String p_cat_id) {
        this.p_cat_id = p_cat_id;
    }

    public String getP_status() {
        return p_status;
    }

    public void setP_status(String p_status) {
        this.p_status = p_status;
    }

    public String getP_stock() {
        return p_stock;
    }

    public void setP_stock(String p_stock) {
        this.p_stock = p_stock;
    }
}
