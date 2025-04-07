package com.example.doctor_doctor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NursePage extends AppCompatActivity {
password_db dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nurse_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new password_db(this);

        EditText uname = findViewById(R.id.uname);
        Button approve = findViewById(R.id.approve);
        Button logout = findViewById(R.id.logout);
        EditText view = findViewById(R.id.view);

        view.setText(dbHelper.getPendingPatients());

        approve.setOnClickListener(v -> {
            dbHelper.approvePatient(uname.getText().toString());
            view.setText(dbHelper.getPendingPatients());
        });

        logout.setOnClickListener(v -> finish());
    }
}