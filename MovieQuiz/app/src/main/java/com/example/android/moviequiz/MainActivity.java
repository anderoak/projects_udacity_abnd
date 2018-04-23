package com.example.android.moviequiz;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private int score;

    RadioButton correctQuestion1;
    boolean questionIsChecked1;

    RadioButton correctQuestion2;
    boolean questionIsChecked2;

    CheckBox correctQuestion3a;
    boolean questionIsChecked3a;

    CheckBox correctQuestion3b;
    boolean questionIsChecked3b;

    CheckBox correctQuestion3c;
    boolean questionIsChecked3c;

    CheckBox correctQuestion3d;
    boolean questionIsChecked3d;

    EditText correctQuestion4;
    String questionAnswer4;

    RadioButton correctQuestion5;
    boolean questionIsChecked5;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.checkButton);
        button.setOnClickListener(buttonCheck);

        correctQuestion1 = findViewById(R.id.question1_answer3);
        correctQuestion2 = findViewById(R.id.question2_answer2);
        correctQuestion3a = findViewById(R.id.question3_answer1);
        correctQuestion3b = findViewById(R.id.question3_answer2);
        correctQuestion3c = findViewById(R.id.question3_answer3);
        correctQuestion3d = findViewById(R.id.question3_answer4);
        correctQuestion4 = findViewById(R.id.question4_answer1);
        correctQuestion5 = findViewById(R.id.question5_answer4);
    }

    @SuppressLint({"StringFormatMatches", "Registered"})

    // Verify answers

    private OnClickListener buttonCheck = new OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            // Tests the correct answer to Question 1.

            questionIsChecked1 = correctQuestion1.isChecked();

            if (questionIsChecked1) score += 1;

            // Tests the correct answer to Question 2.

            questionIsChecked2 = correctQuestion2.isChecked();

            if (questionIsChecked2) score += 1;

            // Tests the correct answer to Question 3.

            questionIsChecked3a = correctQuestion3a.isChecked();
            questionIsChecked3b = correctQuestion3b.isChecked();
            questionIsChecked3c = correctQuestion3c.isChecked();
            questionIsChecked3d = correctQuestion3d.isChecked();

            if ((questionIsChecked3b && questionIsChecked3d) && !questionIsChecked3a && !questionIsChecked3c) score += 1;

            // Tests the correct answer to Question 4.

            questionAnswer4 = correctQuestion4.getText().toString().toLowerCase();

            if (Objects.equals(questionAnswer4, "dory")) score += 1;

            // Tests the correct answer to Question 5.

            questionIsChecked5 = correctQuestion5.isChecked();

            if (questionIsChecked5) score += 1;

            // Displays message with punctuation.

            if (score == 5) {
                Toast.makeText(MainActivity.this, getString(R.string.congrats), Toast.LENGTH_LONG).show();
            } else if (score == 0) {
                Toast.makeText(MainActivity.this, getString(R.string.missed), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.hit, score), Toast.LENGTH_LONG).show();
            }

            // Reset the score for a new answer scan.

            score = 0;
        }
    };
}
