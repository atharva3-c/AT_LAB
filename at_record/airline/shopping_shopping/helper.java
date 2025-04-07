package com.example.shopping_shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class helper extends SQLiteOpenHelper {
    public helper(Context ctx){
        super(ctx,"logins",null,1);
       // SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + "logins" + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, User TEXT, Pass TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS logins");
        onCreate(db);
    }

    public boolean insert(String uid,String pass){
        ContentValues cv=new ContentValues();
        cv.put("User",uid);
        cv.put("Pass",pass);
        SQLiteDatabase db=this.getWritableDatabase();
        long updated=db.insert("logins",null,cv);
        return updated != -1;
    }

    public int verify(String uid,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT COUNT(*) FROM logins WHERE User = ? AND Pass = ?", new String[]{uid, pass});
        int count=0;
        if(cursor.moveToFirst()){
            count=cursor.getInt(0);
        }
        cursor.close();
        return count;

    }
}
