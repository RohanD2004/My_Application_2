package com.pqr.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 4);
    }

//    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(tabname TEXT primary key, batchcode TEXT, qty TEXT,price TEXT,exp TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    private SQLiteDatabase db;
    public Boolean addstock(String tab_name, String batch_code, String tab_qty,String tab_price,String tab_exp)
    {
       db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tabname", tab_name);
        contentValues.put("batchcode", batch_code);
        contentValues.put("qty", tab_qty);
        contentValues.put("price", tab_price);
        contentValues.put("exp", tab_exp);
        long result=db.insert("Userdetails", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updatestock(String batch_code, String tab_qty,String tab_price,String tab_exp)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("batchcode", batch_code);
        contentValues.put("qty", tab_qty);
        contentValues.put("price", tab_price);
        contentValues.put("exp", tab_exp);
        Cursor cursor = db.rawQuery("Select * from Userdetails where batchcode = ?", new String[]{batch_code});
        if (cursor.getCount() > 0) {
            long result = db.update("Userdetails", contentValues, "batchcode=?", new String[]{batch_code});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor get_row_data(String tab_name)
    {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Userdetails where tabname = ?", new String[]{tab_name});
        return  cursor;
    }


//    public Boolean deletedata (String name)
//    {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
//        if (cursor.getCount() > 0) {
//            long result = DB.delete("Userdetails", "name=?", new String[]{name});
//            if (result == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }

    public Cursor getstock ()
    {
       db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Userdetails", null);
        return cursor;
    }


}
