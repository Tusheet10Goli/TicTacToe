package com.example.tictactoe;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserUpdater {
    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users");

    public static void updateUserAddWin(String username) {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user_check = data.getValue(User.class);
                    if (user_check.getName() != null) {
                        if (!user_check.getName().equals(username)) {
                            continue;
                        }
                        int curr_wins = user_check.getNum_wins();
                        db.child(username).child("num_wins").setValue(curr_wins + 1);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
    }

    public static void updateUserAddLoss(String username) {
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user_check = data.getValue(User.class);
                    if (user_check.getName() != null) {
                        if (!user_check.getName().equals(username)) {
                            continue;
                        }
                        int curr_losses = user_check.getNum_wins();
                        db.child(username).child("num_losses").setValue(curr_losses + 1);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
    }
}
