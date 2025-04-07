package com.example.shopping_shopping;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class Selection extends AppCompatActivity {
    ListView listView;
    helper2 dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listView);
        dbHelper = new helper2(this);
        EditText dep=findViewById(R.id.editTextNumber);
        EditText arr=findViewById(R.id.editTextNumber2);

        Button res=findViewById(R.id.button);
        insertdummy();
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        HashMap<String, String> selectedItem = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                        String val = selectedItem.get("name");
                        Toast.makeText(Selection.this, "Clicked: " + val, Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(Selection.this,billing.class);
                        intent.putExtra("op",val);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    private void insertdummy() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Flights"); // Clears previous entries

        dbHelper.insert(123,"Indigo-6E",14.5,14,900);
        dbHelper.insert(467,"Etihad",14,10,800);
        dbHelper.insert(7896,"France Air",14,8,700);//[9am after dept and before 3 pm arrival]
        dbHelper.insert(65,"gulf Air",16,10,600);
        dbHelper.insert(98,"mexican Air",20,16,500);
    }

    public void fetch() {
        // Fetch student data
        ArrayList<HashMap<String, String>> studentData = dbHelper.getAllStudents(15,9);

        // Define mapping between HashMap keys and ListView items

        int[] to = {android.R.id.text1,android.R.id.text2};

        // Use SimpleAdapter
        String[] from = {"name","Details"};
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                studentData,
                android.R.layout.simple_list_item_2,
                from,
                to
        );

        listView.setAdapter(adapter);
    }
}