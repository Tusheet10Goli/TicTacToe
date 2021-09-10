package com.example.tictactoe;

public class User {
    String name;
    String password;
    int num_wins;
    int num_losses;

    public User() {
    }

    public User(String name, String password, int num_wins, int num_losses) {
        this.name = name;
        this.password = password;
        this.num_wins = num_wins;
        this.num_losses = num_losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNum_wins() {
        return num_wins;
    }

    public void setNum_wins(int num_wins) {
        this.num_wins = num_wins;
    }

    public int getNum_losses() {
        return num_losses;
    }

    public void setNum_losses(int num_losses) {
        this.num_losses = num_losses;
    }
}
