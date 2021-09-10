package com.example.tictactoe;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(StartScreen.this, LoginPlayer1.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}
