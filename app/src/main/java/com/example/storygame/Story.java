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
    static MainActivity.GameView gameView;

    View curView;

    String name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);
        curView = root;
        return root;
    }

    public void playGame()
    {
        final TextView textView = curView.findViewById(R.id.main_text);

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

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(gameView);

            final EditText editText = curView.findViewById(R.id.name);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(editText);

            // set dialog message
            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
            String greeting = "Nice to meet you " + editText + "!";
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
}
