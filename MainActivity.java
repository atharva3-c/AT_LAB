package com.example.myapplication2222;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private Button submitButton;
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

        radioGroup = findViewById(R.id.radioGroup);  // RadioGroup containing the options
        submitButton = findViewById(R.id.button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected option
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    // Get the selected RadioButton
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedAnswer = selectedRadioButton.getText().toString();

                    // Define the correct answer (example: Option1 is correct)
                    String correctAnswer = "Option1";

                    // Check if the selected answer is correct
                    int score = selectedAnswer.equals(correctAnswer) ? 1 : 0;

                    // Create an intent to pass the score to ResultsActivity
                    Intent intent = new Intent(MainActivity.this, results.class);
                    intent.putExtra("score", score);  // Passing the score
                    startActivity(intent);
                } else {
                    // If no option is selected, show a message
                    Toast.makeText(MainActivity.this, "Please select an option!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}