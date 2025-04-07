package com.example.student_crud;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks,tv2;
    Button btnAddData,btnDropTable,fbtn,delrow;
    TextView tv;
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
        myDb = new DatabaseHelper(this);    //this is going to call the constructor of this DatabaseHelper class.

        editName = findViewById(R.id.editTextText);
        editSurname = findViewById(R.id.editTextText2);
        editMarks = findViewById(R.id.editTextText3);
        btnAddData = findViewById(R.id.button);
        btnDropTable=findViewById(R.id.button3);
        delrow=findViewById(R.id.button5);
        Button updatebtn=findViewById(R.id.button4);
        fbtn=findViewById(R.id.button2);
        tv=findViewById(R.id.textView2);
        tv2=findViewById(R.id.editTextText4);

        delrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteroww();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateon =tv2.getText().toString();
                String s1=editName.getText().toString(),s2=editSurname.getText().toString(),s3=editMarks.getText().toString();
                boolean isupdated = myDb.update(updateon,s1,s2,s3);
                if (isupdated) {
                    Toast.makeText(MainActivity.this, "Student updated successfully", Toast.LENGTH_SHORT).show();
                    fetch(); // Refresh the displayed data
                } else {
                    Toast.makeText(MainActivity.this, "Student not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetch();
            }
        });
        AddData();
        btnDropTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.dropTable();  // Calling the dropTable method to drop the table
                Toast.makeText(MainActivity.this, "Table Dropped", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteroww() {
        String s1=editName.getText().toString(),s2=editSurname.getText().toString(),s3=editMarks.getText().toString();
        boolean isDeleted = myDb.deleteStudent(s1,s3);
        if (isDeleted) {
            Toast.makeText(MainActivity.this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
            fetch(); // Refresh the displayed data
        } else {
            Toast.makeText(MainActivity.this, "Student not found", Toast.LENGTH_SHORT).show();
        }

    }
    public void AddData()   {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                String s1=editName.getText().toString(),s2=editSurname.getText().toString(),s3=editMarks.getText().toString();
                if(s1.isEmpty()||s2.isEmpty()||s3.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                }
                else{
                    boolean isInserted = myDb.insertData(s1,s2,s3);

                    if(isInserted)
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void fetch(){
        Cursor res = myDb.getAllData();  // Get the data
        StringBuilder stringBuilder = new StringBuilder();

        // Loop through the Cursor and append data to the StringBuilder
        if (res.moveToFirst()) {
            do {
                // Use getColumnIndexOrThrow to ensure the column exists and throw an exception if not
                try {
                    String id = res.getString(res.getColumnIndexOrThrow(DatabaseHelper.COL_1));
                    String name = res.getString(res.getColumnIndexOrThrow(DatabaseHelper.COL_2));
                    String surname = res.getString(res.getColumnIndexOrThrow(DatabaseHelper.COL_3));
                    String marks = res.getString(res.getColumnIndexOrThrow(DatabaseHelper.COL_4));

                    // Append each row data with a space separating columns
                    stringBuilder.append(id).append(" ")
                            .append(name).append(" ")
                            .append(surname).append(" ")
                            .append(marks).append("\n"); // Add a new line after each row
                } catch (IllegalArgumentException e) {
                    // If column is not found, you can log the error or handle it
                    Log.e("FetchData", "Column not found: " + e.getMessage());
                }
            } while (res.moveToNext());
        }

        // Set the string builder data into the TextView
        tv.setText(stringBuilder.toString());

        // Close the cursor when you're done
        res.close();
    }

}