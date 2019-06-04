package com.example.tictactoe;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] bt = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView tv1, tv2;


    //OnCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                String btid = "bt" + i + j;
                int resID = getResources().getIdentifier(btid, "id", getPackageName());
                bt[i][j] = (Button) findViewById(resID);
                bt[i][j].setOnClickListener(this);
            }
        }

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }


    //On click functionality
    @Override
    public void onClick(View v) {
        //Draw only if space is empty
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        //Drawing block
        if (player1Turn) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.RED);
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.BLACK);
        }
        roundCount++;

        //Logic block - check for winner or draw and then switch turns
        if (checkForWin()){
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }


    //Checks for a winner
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                field[i][j] = bt[i][j].getText().toString();
            }
        }

        //row check
        for (int i = 0; i < 3; i++) {
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        //column check
        for (int i = 0; i < 3; i++) {
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        //diagonal 1 check
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        //diagonal 2 check
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }


    //Player 1 wins
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }


    //Player 2 wins
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }


    //Draw
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }


    //Update the points
    private void updatePointsText() {
        tv1.setText("Player 1: " + player1Points);
        tv2.setText("Player 2: " + player2Points);
    }


    //Reset board
    private void resetBoard() {
        Handler mHandler = new Handler();

        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {

                //Reset X and O
                for (int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        bt[i][j].setText("");
                    }
                }

                roundCount = 0;
                player1Turn = true;
            }
        };

        mHandler.postDelayed(mRunnable, 2000);
    }


    //Completely reset the game
    private void resetGame() {
        Toast.makeText(this, "Game is reset!", Toast.LENGTH_SHORT).show();
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }


    //Save variable data on changing orientation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }


    //Restore saved variables on orientation change
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}