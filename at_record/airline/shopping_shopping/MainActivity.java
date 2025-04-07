package com.example.shopping_shopping;

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

    helper h=new helper(this);
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
        Button btn =findViewById(R.id.button2);
        EditText pw=findViewById(R.id.editTextTextPassword);
        EditText un=findViewById(R.id.editTextText);
        Switch sw=findViewById(R.id.switch1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid=un.getText().toString();
                String pswd=pw.getText().toString();
                if(sw.isChecked()){
                    h.insert(uid,pswd);
                    Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                }
                else{
                    if((h.verify(uid,pswd))!=0){
                        Intent intent=new Intent(MainActivity.this,Selection.class);
                        intent.putExtra("userid",uid);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}