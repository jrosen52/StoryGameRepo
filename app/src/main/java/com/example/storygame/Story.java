package com.example.storygame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;

import androidx.annotation.NonNull;

public class Story {
    MainActivity.GameView gameView;

    View curView;

    String name;

    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);
        curView = root;
        textView = root.findViewById(R.id.main_text);
        return root;
    }

    public void playGame()
    {
        final Handler handler = new Handler();

        float x = gameView.getX();
        if(x == 50)
        {
            textView.setText("Hello Adventurer!");
            handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Welcome to the Spooky Dungeon!");
            }
        }, 5000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText("What is your name?");
                }
            }, 5000);

            name = gameView.popUp();
            String greeting = "Nice to meet you " + name + "!";
            textView.setText(greeting);
            gameView.changeCanvas(1);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText("If you're looking for treasure,");
                }
            }, 5000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText("you'll find it in the bottom right corner.");
                }
            }, 5000);

        }
    }

    public void playRight()
    {
        final Handler handler = new Handler();

        textView.setText("You found the treasure!");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("You win!");
            }
        }, 5000);
        gameView.wonGame();
    }

    public void playLeft()
    {
        final Handler handler = new Handler();
        textView.setText("You found the treasure!");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("You lose!");
            }
        }, 5000);

    }
}

