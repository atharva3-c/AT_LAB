package com.example.doctor_doctor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PatientPage extends AppCompatActivity {
    password_db dbHelper;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText illness = findViewById(R.id.illness);
        Spinner start = findViewById(R.id.start);
        Spinner end = findViewById(R.id.end);
        Button submit = findViewById(R.id.submit);
        Button fetch = findViewById(R.id.fetch);
        EditText result = findViewById(R.id.result);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"1", "2", "3", "4", "5", "6", "7"});
        start.setAdapter(adapter);
        end.setAdapter(adapter);
        uid = getIntent().getIntExtra("uid", -1);


        submit.setOnClickListener(v -> {
            dbHelper.insertApplication(uid + "", illness.getText().toString(),
                    start.getSelectedItem().toString(), end.getSelectedItem().toString());
            finish();
        });

        fetch.setOnClickListener(v -> {
            result.setText(dbHelper.getApplicationJson(uid + ""));
        });
    }
    }
