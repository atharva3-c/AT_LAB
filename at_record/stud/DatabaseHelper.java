package com.example.student_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "student_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";

    public DatabaseHelper(Context context) {   // when the constructor is called Database will be created.
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();      // creating the instance of the database- this is going to create your DB and Table.
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //to create Table
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT, MARKS INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  //Drop the table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean deleteStudent(String name,String marks) {

        int marksValue = Integer.parseInt(marks);
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME, "NAME = ? AND MARKS < ?", new String[]{name,String.valueOf(marksValue)});
        return deletedRows > 0;  // Returns true if at least one row was deleted
    }
    public boolean update(String basis,String s1,String s2, String s3){
        ContentValues values = new ContentValues();
        values.put("NAME",s1);
        values.put("SURNAME",s2);
        values.put("MARKS", Integer.parseInt(s3));
        SQLiteDatabase db = this.getWritableDatabase();
        int updatedRows = db.update("student_table", values, "NAME = ?", new String[]{basis});

        return updatedRows>0;
    }

    public boolean insertData(String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();      // creating the instance of the database- this is going to create your DB and Table.
        ContentValues contentValues = new ContentValues();  //import the required classes; now we are taking content values instance
        // and placing the data into the columns.
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

}