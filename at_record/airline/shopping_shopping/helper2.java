package com.example.shopping_shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class helper2 extends SQLiteOpenHelper {
    public helper2(Context ctx){
        super(ctx,"Flights",null,2);
        // SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE Flights (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "flight_id INTEGER, " +
                        "name TEXT, " +
                        "departure REAL, " +
                        "arrival REAL," +
                        "price REAL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Flights");
        onCreate(db);
    }
    public ArrayList<HashMap<String, String>> getAllStudents(double a, double b) {
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "Flights " + "WHERE arrival <= ? AND departure >= ?", new String[]{String.valueOf(a), String.valueOf(b)});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();

                student.put("name", cursor.getString(2));
                student.put("Details",
                        "Flight ID: " + cursor.getString(1) +
                                " | Departure: " + cursor.getString(3) +
                                " | Arrival: " + cursor.getString(4));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }

    public Cursor ask(String name){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT price from Flights where name = ?",new String[]{name});

    }
    public boolean insert(int fid, String flight, double arr, double dept,double price){
        ContentValues cv=new ContentValues();
        cv.put("flight_id",fid);
        cv.put("name",flight);
        cv.put("arrival",arr);
        cv.put("departure",dept);
        cv.put("price",price);

        SQLiteDatabase db=this.getWritableDatabase();
        long updated=db.insert("Flights",null,cv);
        return updated != -1;
    }


}
