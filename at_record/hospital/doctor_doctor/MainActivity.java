package com.example.doctor_doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
password_db dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        password_db help=new password_db(this);
        EditText username=findViewById(R.id.editTextText);
        EditText password=findViewById(R.id.editTextTextPassword);
        Button sub=findViewById(R.id.button);
        Switch toggle=findViewById(R.id.switch1);
        sub.setOnClickListener(v -> {
            String id = username.getText().toString();
            String pw = password.getText().toString();
            if (!toggle.isChecked()) {
                dbHelper.insertUser(id, pw);
            }
            if (dbHelper.checkUser(id, pw)) {
                int uid = Integer.parseInt(id);
                if (uid < 50) startActivity(new Intent(this, PatientPage.class).putExtra("uid", uid));
                else if (uid < 100) startActivity(new Intent(this, NursePage.class));
                else startActivity(new Intent(this, DoctorPage.class));
            }
        });
    }
}