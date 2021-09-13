package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class LoginPlayer2 extends AppCompatActivity {

    private static String player_name;
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
                player_name = name.getText().toString();
            }
        });
    }

    public static void updateWin() {
        UserUpdater.updateUserAddWin(player_name);
    }

    public static void updateLoss() {
        UserUpdater.updateUserAddLoss(player_name);
    }

    private void validate(String name, String pass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference().child("users");

        if (!name.isEmpty() && !pass.isEmpty()) {
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        User user_check = data.getValue(User.class);
                        if (user_check.getName() != null) {
                            if (!user_check.getName().equals(name)) {
                                continue;
                            }
                            if (user_check.getPassword() != null && user_check.getPassword().equals(pass)) {
                                Intent intent = new Intent(LoginPlayer2.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginPlayer2.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                ctr--;
                                tv.setText("Number Of Attempts Remaining : " + ctr);
                                if (ctr == 0) {
                                    ctr = 5;
                                    bt.setEnabled(false);
                                    Intent it = new Intent(LoginPlayer2.this, StartScreen.class);
                                    startActivity(it);
                                }
                                break;
                            }
                        } else {
                            User user = new User(name, pass, 0, 0);
                            db.child(name).setValue(user);
                            Intent intent = new Intent(LoginPlayer2.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    Toast.makeText(LoginPlayer2.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginPlayer2.this, "Illegal Username/Password", Toast.LENGTH_SHORT).show();
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
