package com.example.tictactoe;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Board boardObject = new Board();
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
        tv1.setText(LoginPlayer1.getName() + ": " + 0);
        tv2.setText(LoginPlayer2.getName() + ": " + 0);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                String btid = "bt" + i + j;
                int resID = getResources().getIdentifier(btid, "id", getPackageName());
                boardObject.initializeBoardLocation(i, j, (Button) findViewById(resID));
                Button btn = boardObject.getBoardLocation(i, j);
                btn.setOnClickListener(this);
            }
        }

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        Button end = (Button) findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
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
        boardObject.performTurn((Button) v);
        int winCondition = boardObject.checkWin();

        //Logic block - check for winner or draw and then switch turns
        if (winCondition == 1) {
            player1Wins();
        } else if (winCondition == 2) {
            player2Wins();
        } else if (winCondition == 3) {
            draw();
        }
    }

    //Player 1 wins
    private void player1Wins() {
        player1Points++;
        LoginPlayer1.updateWin();
        LoginPlayer2.updateLoss();
        Toast.makeText(this, LoginPlayer1.getName() + " Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    //Player 2 wins
    private void player2Wins() {
        player2Points++;
        LoginPlayer1.updateWin();
        LoginPlayer1.updateLoss();
        Toast.makeText(this, LoginPlayer2.getName() + " Wins!", Toast.LENGTH_SHORT).show();
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
        tv1.setText(LoginPlayer1.getName() + ": " + player1Points);
        tv2.setText(LoginPlayer2.getName() + ": " + player2Points);
    }

    //Reset board
    private void resetBoard() {
        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                boardObject.resetBoard();
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

    //Completely end the game
    private void endGame() {
        Intent intent = new Intent(MainActivity.this, EndScreen.class);
        startActivity(intent);
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