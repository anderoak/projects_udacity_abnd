package com.example.android.voleibolplacar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Inicializa variáveis dos placares do Time A

    int scoreTeamASet1 = 0;
    int scoreTeamASet2 = 0;
    int scoreTeamASet3 = 0;
    int scoreTeamASet4 = 0;
    int scoreTeamASet5 = 0;

    //Inicializa variáveis dos placares do Time B

    int scoreTeamBSet1 = 0;
    int scoreTeamBSet2 = 0;
    int scoreTeamBSet3 = 0;
    int scoreTeamBSet4 = 0;
    int scoreTeamBSet5 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Aumenta placar do Time A - Set 1

    public void addOneTeamASet1(View v) {
        scoreTeamASet1 = scoreTeamASet1 + 1;
        displayScoreTeamASet1(scoreTeamASet1);
    }

    //Aumenta placar do Time A - Set 2

    public void addOneTeamASet2(View v) {
        scoreTeamASet2 = scoreTeamASet2 + 1;
        displayScoreTeamASet2(scoreTeamASet2);
    }

    //Aumenta placar do Time A - Set 3

    public void addOneTeamASet3(View v) {
        scoreTeamASet3 = scoreTeamASet3 + 1;
        displayScoreTeamASet3(scoreTeamASet3);
    }

    //Aumenta placar do Time A - Set 4

    public void addOneTeamASet4(View v) {
        scoreTeamASet4 = scoreTeamASet4 + 1;
        displayScoreTeamASet4(scoreTeamASet4);
    }

    //Aumenta placar do Time A - Set 5

    public void addOneTeamASet5(View v) {
        scoreTeamASet5 = scoreTeamASet5 + 1;
        displayScoreTeamASet5(scoreTeamASet5);
    }

    //Aumenta placar do Time B - Set 1

    public void addOneTeamBSet1(View v) {
        scoreTeamBSet1 = scoreTeamBSet1 + 1;
        displayScoreTeamBSet1(scoreTeamBSet1);
    }

    //Aumenta placar do Time B - Set 2

    public void addOneTeamBSet2(View v) {
        scoreTeamBSet2 = scoreTeamBSet2 + 1;
        displayScoreTeamBSet2(scoreTeamBSet2);
    }

    //Aumenta placar do Time B - Set 3

    public void addOneTeamBSet3(View v) {
        scoreTeamBSet3 = scoreTeamBSet3 + 1;
        displayScoreTeamBSet3(scoreTeamBSet3);
    }

    //Aumenta placar do Time B - Set 4

    public void addOneTeamBSet4(View v) {
        scoreTeamBSet4 = scoreTeamBSet4 + 1;
        displayScoreTeamBSet4(scoreTeamBSet4);
    }

    //Aumenta placar do Time B - Set 5

    public void addOneTeamBSet5(View v) {
        scoreTeamBSet5 = scoreTeamBSet5 + 1;
        displayScoreTeamBSet5(scoreTeamBSet5);
    }

    // Zera todos os placares

    public void resetScores(View v) {
        scoreTeamASet1 = 0;
        displayScoreTeamASet1(scoreTeamASet1);
        scoreTeamASet2 = 0;
        displayScoreTeamASet2(scoreTeamASet2);
        scoreTeamASet3 = 0;
        displayScoreTeamASet3(scoreTeamASet3);
        scoreTeamASet4 = 0;
        displayScoreTeamASet4(scoreTeamASet4);
        scoreTeamASet5 = 0;
        displayScoreTeamASet5(scoreTeamASet5);

        scoreTeamBSet1 = 0;
        displayScoreTeamBSet1(scoreTeamASet1);
        scoreTeamBSet2 = 0;
        displayScoreTeamBSet2(scoreTeamASet2);
        scoreTeamBSet3 = 0;
        displayScoreTeamBSet3(scoreTeamASet3);
        scoreTeamBSet4 = 0;
        displayScoreTeamBSet4(scoreTeamASet4);
        scoreTeamBSet5 = 0;
        displayScoreTeamBSet5(scoreTeamASet5);
    }

    // Diminui placar do Time A - Set 1

    public void removeOneTeamASet1(View v) {
        if (scoreTeamASet1 > 0) {
            scoreTeamASet1 = scoreTeamASet1 - 1;
            displayScoreTeamASet1(scoreTeamASet1);
        }
    }

    // Diminui placar do Time A - Set 2

    public void removeOneTeamASet2(View v) {
        if (scoreTeamASet2 > 0) {
            scoreTeamASet2 = scoreTeamASet2 - 1;
            displayScoreTeamASet2(scoreTeamASet2);
        }
    }

    // Diminui placar do Time A - Set 3

    public void removeOneTeamASet3(View v) {
        if (scoreTeamASet3 > 0) {
            scoreTeamASet3 = scoreTeamASet3 - 1;
            displayScoreTeamASet3(scoreTeamASet3);
        }
    }

    // Diminui placar do Time A - Set 4

    public void removeOneTeamASet4(View v) {
        if (scoreTeamASet4 > 0) {
            scoreTeamASet4 = scoreTeamASet4 - 1;
            displayScoreTeamASet4(scoreTeamASet4);
        }
    }

    // Diminui placar do Time A - Set 5

    public void removeOneTeamASet5(View v) {
        if (scoreTeamASet5 > 0) {
            scoreTeamASet5 = scoreTeamASet5 - 1;
            displayScoreTeamASet5(scoreTeamASet5);
        }
    }

    // Diminui placar do Time B - Set 1

    public void removeOneTeamBSet1(View v) {
        if (scoreTeamBSet1 > 0) {
            scoreTeamBSet1 = scoreTeamBSet1 - 1;
            displayScoreTeamBSet1(scoreTeamBSet1);
        }
    }

    // Diminui placar do Time B - Set 2

    public void removeOneTeamBSet2(View v) {
        if (scoreTeamBSet2 > 0) {
            scoreTeamBSet2 = scoreTeamBSet2 - 1;
            displayScoreTeamBSet2(scoreTeamBSet2);
        }
    }

    // Diminui placar do Time B - Set 3

    public void removeOneTeamBSet3(View v) {
        if (scoreTeamBSet3 > 0) {
            scoreTeamBSet3 = scoreTeamBSet3 - 1;
            displayScoreTeamBSet3(scoreTeamBSet3);
        }
    }

    // Diminui placar do Time B - Set 4

    public void removeOneTeamBSet4(View v) {
        if (scoreTeamBSet4 > 0) {
            scoreTeamBSet4 = scoreTeamBSet4 - 1;
            displayScoreTeamBSet4(scoreTeamBSet4);
        }
    }

    // Diminui placar do Time B - Set 5

    public void removeOneTeamBSet5(View v) {
        if (scoreTeamBSet5 > 0) {
            scoreTeamBSet5 = scoreTeamBSet5 - 1;
            displayScoreTeamBSet5(scoreTeamBSet5);
        }
    }

    // Mostar placar do Time A - Set 1

    public void displayScoreTeamASet1(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_a_set1);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time A - Set 2

    public void displayScoreTeamASet2(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_a_set2);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time A - Set 3

    public void displayScoreTeamASet3(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_a_set3);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time A - Set 4

    public void displayScoreTeamASet4(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_a_set4);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time A - Set 5

    public void displayScoreTeamASet5(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_a_set5);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time B - Set 1

    public void displayScoreTeamBSet1(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_b_set1);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time B - Set 2

    public void displayScoreTeamBSet2(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_b_set2);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time B - Set 3

    public void displayScoreTeamBSet3(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_b_set3);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time B - Set 4

    public void displayScoreTeamBSet4(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_b_set4);
        scoreView.setText(String.valueOf(score));
    }

    // Mostar placar do Time B - Set 5

    public void displayScoreTeamBSet5(int score) {
        TextView scoreView = (TextView) findViewById(R.id.time_b_set5);
        scoreView.setText(String.valueOf(score));
    }
}
