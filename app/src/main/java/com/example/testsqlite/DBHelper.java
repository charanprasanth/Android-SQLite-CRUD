package com.example.testsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table Userdetails(name TEXT primary key, contact TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop table if exists Userdetails");
    }

    public boolean insertuserdata(String name, String contact, String dob){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

        long result = DB.insert("Userdetails", null, contentValues);

        return result != -1;
    }

    public boolean updateuserdata(String name, String contact, String dob){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("contact", contact);
        contentValues.put("dob", dob);

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {

            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});

            return result != -1;
        } else {
            return false;
        }
    }

    public boolean deletedata(String name){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {

            long result = DB.delete("Userdetails", "name=?", new String[]{name});

            return result != -1;
        } else {
            return false;
        }
    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
        return cursor;
    }
}
