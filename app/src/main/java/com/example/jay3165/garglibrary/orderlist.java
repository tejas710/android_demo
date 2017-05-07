package com.example.jay3165.garglibrary;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jay3165 on 4/19/2017.
 */

public class orderlist implements Parcelable {
    public String order_id;
    public String order_no;
    public String bill_no;
    public String cart_total;
    public String order_status;
    public String confirm_status;
    public ArrayList<orderdata> orderdata = new ArrayList<>();


    public orderlist(String order_id, String order_no, String bill_no, String cart_total, String order_status, String confirm_status, ArrayList<com.example.jay3165.garglibrary.orderdata> orderdata) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.bill_no = bill_no;
        this.cart_total = cart_total;
        this.order_status = order_status;
        this.confirm_status = confirm_status;
        this.orderdata = orderdata;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getCart_total() {
        return cart_total;
    }

    public void setCart_total(String cart_total) {
        this.cart_total = cart_total;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getConfirm_status() {
        return confirm_status;
    }

    public void setConfirm_status(String confirm_status) {
        this.confirm_status = confirm_status;
    }

    public ArrayList<com.example.jay3165.garglibrary.orderdata> getOrderdata() {
        return orderdata;
    }

    public void setOrderdata(ArrayList<com.example.jay3165.garglibrary.orderdata> orderdata) {
        this.orderdata = orderdata;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_id);
        dest.writeString(this.order_no);
        dest.writeString(this.bill_no);
        dest.writeString(this.cart_total);
        dest.writeString(this.order_status);
        dest.writeString(this.confirm_status);
        dest.writeList(this.orderdata);
    }

    protected orderlist(Parcel in) {
        this.order_id = in.readString();
        this.order_no = in.readString();
        this.bill_no = in.readString();
        this.cart_total = in.readString();
        this.order_status = in.readString();
        this.confirm_status = in.readString();
        this.orderdata = new ArrayList<com.example.jay3165.garglibrary.orderdata>();
        in.readList(this.orderdata, com.example.jay3165.garglibrary.orderdata.class.getClassLoader());
    }

    public static final Creator<orderlist> CREATOR = new Creator<orderlist>() {
        @Override
        public orderlist createFromParcel(Parcel source) {
            return new orderlist(source);
        }

        @Override
        public orderlist[] newArray(int size) {
            return new orderlist[size];
        }
    };
}
