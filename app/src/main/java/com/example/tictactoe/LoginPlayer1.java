package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class LoginPlayer1 extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private TextView tv;
    private Button bt;
    private int ctr  = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference("users");

        if (!name.isEmpty() && !pass.isEmpty()) {
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        if (data.child(name).exists()) {
                            String password = data.child(name).child("password").toString();
                            Log.e("LoginPlayer1", password);

                            if (password.equals(pass)) {
                                Intent intent = new Intent(LoginPlayer1.this, LoginPlayer2.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginPlayer1.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                ctr--;
                                tv.setText("Number Of Attempts Remaining : " + ctr);
                                if (ctr == 0) {
                                    ctr = 5;
                                    bt.setEnabled(false);
                                    Intent it = new Intent(LoginPlayer1.this, StartScreen.class);
                                    startActivity(it);
                                }
                            }
                        } else {
                            User user = new User(name, pass, 0, 0);
                            db.child(name).setValue(user);
                            Intent intent = new Intent(LoginPlayer1.this, LoginPlayer2.class);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    Toast.makeText(LoginPlayer1.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginPlayer1.this, "Illegal Username/Password", Toast.LENGTH_SHORT).show();
            ctr--;
            tv.setText("Number Of Attempts Remaining : " + ctr);
            if (ctr == 0) {
                ctr = 5;
                bt.setEnabled(false);
                Intent it = new Intent(LoginPlayer1.this, StartScreen.class);
                startActivity(it);
            }
        }
    }
}
