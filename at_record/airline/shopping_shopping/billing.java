package com.example.shopping_shopping;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class billing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_billing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String op=getIntent().getStringExtra("op");
        helper2 db=new helper2(this);
        Cursor cr=db.ask(op);
        StringBuilder sb=new StringBuilder();
        EditText dis=findViewById(R.id.editTextTextMultiLine);
        if(cr.moveToFirst()){
            do{
                double price=cr.getDouble(0);
                sb.append(price).append("\n");
            }while(cr.moveToNext());
            dis.setText(sb.toString());
        }
        else{
            dis.setText("EMPTY");
        }
        cr.close();

    }


}