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

        float x = 40;
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
                    textView.setText("you'll find it in the red chest.");
                }
            }, 5000);

        }
    }

    public void playRed()
    {
        final Handler handler = new Handler();

        textView.setText("You found the treasure!");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("You win!");
            }
        }, 5000);
        gameView.wonGame(1);
    }

    public void playBlue1()
    {
        final Handler handler = new Handler();
        textView.setText("That's... not the treasure");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("That chest has many spiders");
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Avoid the spiders!");
            }
        }, 5000);
        gameView.releaseSpiders();
    }

    public void playBlue2()
    {
        final Handler handler = new Handler();
        textView.setText("Why did you open it again!?");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Now you've woken up the snakes!");
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Avoid the snakes!");
            }
        }, 5000);
        gameView.releaseSnakes();

    }

    public void playBlue3()
    {
        final Handler handler = new Handler();
        textView.setText("I can't believe you've done this");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("The dead are rising from their graves!");
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Avoid the skeletons!");
            }
        }, 5000);
        gameView.releaseSkeletons();
    }

    public void playBlue4()
    {
        final Handler handler = new Handler();
        textView.setText("No No No! Now You've done it!");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("You just released the ...");
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Wait. I think that's it.");
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("That must mean you win!");
            }
        }, 5000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("But you still don't get treasure");
            }
        }, 5000);
        gameView.wonGame(0);
    }
}

