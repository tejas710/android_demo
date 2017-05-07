package com.example.jay3165.garglibrary;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jay3165 on 4/19/2017.
 */

public class orderdata implements Parcelable {


    public String p_id;
    public String quantity;
    public String price;
    public String p_name;

    public orderdata(String p_id, String quantity, String price,String p_name) {
        this.p_id = p_id;
        this.quantity = quantity;
        this.price = price;
        this.p_name=p_name;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getP_name(){return p_name;}
    public void  setP_name(String p_name) {this.p_name = p_name;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.p_id);
        dest.writeString(this.quantity);
        dest.writeString(this.price);
        dest.writeString(this.p_name);
    }

    protected orderdata(Parcel in) {
        this.p_id = in.readString();
        this.quantity = in.readString();
        this.price = in.readString();
        this.p_name = in.readString();
    }

    public static final Creator<orderdata> CREATOR = new Creator<orderdata>() {
        @Override
        public orderdata createFromParcel(Parcel source) {
            return new orderdata(source);
        }

        @Override
        public orderdata[] newArray(int size) {
            return new orderdata[size];
        }
    };
}
