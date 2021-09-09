package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPlayer2 extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private TextView tv;
    private Button bt;
    private int ctr  = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        tv = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);
        tv.setText("Number Of Attempts Remaining : " + ctr);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), pass.getText().toString());
            }
        });
    }

    private void validate(String name, String pass) {
        if(name.equals("tusheet") && pass.equals("1234") || name.equals("") && pass.equals("")) {
            Intent intent = new Intent(LoginPlayer2.this, MainActivity.class);
            startActivity(intent);
        } else {
            ctr--;
            tv.setText("Number Of Attempts Remaining : " + ctr);
            if (ctr == 0) {
                ctr = 5;
                bt.setEnabled(false);
                Intent it = new Intent(LoginPlayer2.this, StartScreen.class);
                startActivity(it);
            }
        }
    }
}
