package com.example.jay3165.garglibrary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jay3165 on 3/28/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    public static final String TABLE_NAME="product123";
    public static final String P_NAME="p_name";
    public static final String P_IMAGE= "p_image";
    public static final String P_PRICE="p_price";
    public static final String P_QTY="p_qty";
    public static final String P_TOTAL="p_total";
    private static final String P_ID = "p_id";
    public static final String DATABASE_NAME = "productinfo123";
    public static final int DATABASE_VERSION = 1;

    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "";
        query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " + P_ID + " INTEGER PRIMARY KEY, " + P_NAME + " TEXT, " + P_IMAGE + " TEXT, " + P_PRICE + " TEXT, " + P_QTY + " TEXT, " + P_TOTAL + " TEXT) ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryy = "";
        queryy = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(queryy);
        onCreate(db);

    }
    public ArrayList<BookAddToCart> getAllBookDetails()
    {
        ArrayList<BookAddToCart> productlist=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.rawQuery("select * from " + TABLE_NAME,null);
        cursor.moveToFirst();

        while (cursor.isAfterLast()==false)
        {
            BookAddToCart bookAddToCart = new BookAddToCart(Integer.parseInt(cursor.getString(0)),cursor.getString(cursor.getColumnIndex(P_NAME)),cursor.getString(cursor.getColumnIndex(P_IMAGE)),cursor.getString(cursor.getColumnIndex(P_PRICE)),cursor.getString(cursor.getColumnIndex(P_QTY)),cursor.getString(cursor.getColumnIndex(P_TOTAL)));
            productlist.add(bookAddToCart);
            cursor.moveToNext();
        }

        return productlist;
    }


    public void addproduct(BookAddToCart bookAddToCart)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(P_NAME,bookAddToCart.getP_name());
        contentValues.put(P_IMAGE,bookAddToCart.getP_image());
        contentValues.put(P_PRICE,bookAddToCart.getP_price());
        contentValues.put(P_QTY,bookAddToCart.getP_qty());
        contentValues.put(P_TOTAL,bookAddToCart.getP_total());
        db.insert(TABLE_NAME,null,contentValues);
        Log.i("insert","1");
        db.close();
    }
public void deleteproduct(BookAddToCart bookAddToCart){

    SQLiteDatabase db=this.getWritableDatabase();



    db.delete(TABLE_NAME,
            "p_id = ? ",
            new String[] { Integer.toString(bookAddToCart.getP_id()) });



}

}
