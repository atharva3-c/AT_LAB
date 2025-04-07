package com.example.doctor_doctor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DoctorPage extends AppCompatActivity {
password_db dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.doctor_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new password_db(this);

        EditText from = findViewById(R.id.starttime);
        EditText to = findViewById(R.id.endtime);
        Button filter = findViewById(R.id.filter);
        TextView view = findViewById(R.id.output);
        EditText username = findViewById(R.id.approveuser);
        Button approve = findViewById(R.id.approve_appointment);
        Button logout = findViewById(R.id.logout);

        filter.setOnClickListener(v -> {
            view.setText(dbHelper.getApprovedPatientsInRange(
                    Integer.parseInt(from.getText().toString()),
                    Integer.parseInt(to.getText().toString())));
        });

        approve.setOnClickListener(v -> {
            dbHelper.approveAppointment(username.getText().toString());
            view.setText("");
        });

        logout.setOnClickListener(v -> finish());
    }
}