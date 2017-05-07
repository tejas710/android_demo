package com.example.jay3165.garglibrary;

/**
 * Created by jay3165 on 3/29/2017.
 */

public class BookAddToCart {
    private int p_id;
    private  String p_name;
    private  String p_image;
    private  String p_price;
    private  String p_qty;
    private  String p_total;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_qty() {
        return p_qty;
    }

    public void setP_qty(String p_qty) {
        this.p_qty = p_qty;
    }

    public String getP_total() {
        return p_total;
    }

    public void setP_total(String p_total) {
        this.p_total = p_total;
    }

    public BookAddToCart(int p_id, String p_name, String p_image, String p_price, String p_qty, String p_total) {

        this.p_id = p_id;
        this.p_name = p_name;
        this.p_image = p_image;
        this.p_price = p_price;
        this.p_qty = p_qty;
        this.p_total = p_total;
    }
}
