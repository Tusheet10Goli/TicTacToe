package com.example.tictactoe;
import android.graphics.Color;
import android.widget.Button;

public class Board {
    private Button[][] board;
    private int playerTurn;
    private int turnCount;

    public Board() {
        board = new Button[3][3];
        playerTurn = 1;
        turnCount = 0;
    }

    public void initializeBoardLocation(int row, int column, Button btn) {
        board[row][column] = btn;
    }

    public Button getBoardLocation(int row, int column) {
        return board[row][column];
    }

    public void performTurn(Button btn) {
        if (playerTurn == 1) {
            btn.setText("X");
            btn.setTextColor(Color.RED);
        } else {
            btn.setText("O");
            btn.setTextColor(Color.BLACK);
        }
        turnCount++;
        playerTurn =  playerTurn == 2 ? 1: 2;
    }

    public int checkWin() {
        if (checkForWin()) {
            return playerTurn;
        }
        if (turnCount == 9) {
            return 3;
        }
        return 0;
    }

    public void resetBoard() {
        //Reset X and O
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j].setText("");
            }
        }
        turnCount = 0;
        playerTurn = 1;
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                field[i][j] = board[i][j].getText().toString();
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
}
