package com.example.doctor_doctor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class password_db extends SQLiteOpenHelper {

        public password_db(Context c) {
            super(c, "clinic", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE logs(pid INTEGER, pass TEXT)");
            db.execSQL("CREATE TABLE applications(pid INTEGER, illness TEXT, start INTEGER, endd INTEGER, approved INTEGER, appointment_scheduled INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS logs");
            db.execSQL("DROP TABLE IF EXISTS applications");
            onCreate(db);
        }

        public void insertUser(String id, String pw) {
            ContentValues cv = new ContentValues();
            cv.put("pid", id);
            cv.put("pass", pw);
            getWritableDatabase().insert("logs", null, cv);
        }

        public boolean checkUser(String id, String pw) {
            Cursor cs = getReadableDatabase().rawQuery("SELECT * FROM logs WHERE pid = ?", new String[]{id});
            boolean ok = cs.moveToFirst();
            cs.close();
            return ok;
        }

        public void insertApplication(String id, String illness, String start, String end) {
            ContentValues cv = new ContentValues();
            cv.put("pid", id);
            cv.put("illness", illness);
            cv.put("start", Integer.parseInt(start));
            cv.put("end", Integer.parseInt(end));
            cv.put("approved", 0);
            cv.put("appointment_scheduled", 0);
            getWritableDatabase().insert("applications", null, cv);
        }

        public String getApplicationJson(String id) {
            Cursor cs = getReadableDatabase().rawQuery("SELECT * FROM applications WHERE pid=?", new String[]{id});
            if (!cs.moveToFirst()) return "Not Found";
            String json = "{\n" +
                    "  \"pid\": " + cs.getInt(0) + ",\n" +
                    "  \"illness\": \"" + cs.getString(1) + "\",\n" +
                    "  \"start\": " + cs.getInt(2) + ",\n" +
                    "  \"end\": " + cs.getInt(3) + ",\n" +
                    "  \"approved\": " + cs.getInt(4) + ",\n" +
                    "  \"appointment_scheduled\": " + cs.getInt(5) + "\n}";
            cs.close();
            return json;
        }

        public String getPendingPatients() {
            Cursor cs = getReadableDatabase().rawQuery("SELECT pid, illness, approved FROM applications WHERE approved = 0", null);
            StringBuilder sb = new StringBuilder();
            while (cs.moveToNext()) {
                sb.append("PID: ").append(cs.getInt(0)).append(", Illness: ").append(cs.getString(1))
                        .append(", Approved: ").append(cs.getInt(2)).append("\n");
            }
            cs.close();
            return sb.toString();
        }

        public void approvePatient(String id) {
            getWritableDatabase().execSQL("UPDATE applications SET approved=1 WHERE pid = ?", new String[]{id});
        }

        public String getApprovedPatientsInRange(int from, int to) {
            Cursor cs = getReadableDatabase().rawQuery("SELECT * FROM applications WHERE approved=1 AND start>=? AND endd<=?", new String[]{from + "", to + ""});
            StringBuilder sb = new StringBuilder();
            while (cs.moveToNext()) {
                sb.append("PID: ").append(cs.getInt(0)).append(", Illness: ").append(cs.getString(1))
                        .append(", Start: ").append(cs.getInt(2)).append(", End: ").append(cs.getInt(3)).append("\n");
            }
            cs.close();
            return sb.toString();
        }

        public void approveAppointment(String id) {
            getWritableDatabase().execSQL("UPDATE applications SET appointment_scheduled=1 WHERE pid=?", new String[]{id});
        }
}
