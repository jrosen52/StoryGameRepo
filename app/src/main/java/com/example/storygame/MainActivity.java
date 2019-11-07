package com.example.storygame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{

    GameView gameView;
    Story story;

    public RectF chest1;
    public RectF chest2;

    public String playerName;

    public boolean nameAsked = false;
    public boolean spidersReleased = false;
    public boolean snakesReleased = false;
    public boolean skeletonsReleased = false;

    private Bitmap bmp;

    Drawable spider;
    Drawable blueChest;
    Drawable redChest;
    Drawable skel;
    Drawable player;

    public void Sprite(GameView gameView, Bitmap bmp) {

        this.gameView=gameView;

        this.bmp=bmp;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        story = new Story();

        spider = getResources().getDrawable(R.drawable.spider);
        player = getResources().getDrawable(R.drawable.slime);
        skel = getResources().getDrawable(R.drawable.skeleton);
        redChest = getResources().getDrawable(R.drawable.red);
        blueChest = getResources().getDrawable(R.drawable.blue);

        setContentView(gameView);

    }

    class GameView extends SurfaceView implements Runnable
    {

        Thread gameThread = null;
        SurfaceHolder ourHolder;

        volatile boolean playing;

        Canvas canvas;
        Paint paint;

        long fps;

        private long timeThisFrame;

        Bitmap bitmapSlime;

        boolean isMoving = false;

        float walkSpeedPerSecond = 250;

        float slimeXPosition = 10;

        private int frameWidth = 100;
        private int frameHeight = 50;

        private int frameCount = 5;

        private int currentFrame = 0;

        private long lastFrameChangeTime = 0;

        private int frameLengthInMilliseconds = 100;

        int green = Color.argb(255, 120, 197, 87);
        int blue = Color.argb(255, 93, 142, 240);
        int red = Color.argb(255, 240, 65, 93);
        int yellow = Color.argb(255, 228, 235, 42);
        int orange = Color.argb(255, 250, 193, 58);
        int purple = Color.argb(255, 225, 58, 250);
        int white = Color.argb(255, 225, 255, 255);
        int black = Color.argb(255, 0, 0, 0);

        int colNum = 0;

        int colors[] = {Color.argb(255, 0, 0, 0),Color.argb(255, 225, 255, 255),
                Color.argb(255, 120, 197, 87),Color.argb(255, 93, 142, 240),
                Color.argb(255, 240, 65, 93), Color.argb(255, 228, 235, 42),
                Color.argb(255, 250, 193, 58), Color.argb(255, 225, 58, 250)};


        private Rect frameToDraw = new Rect(
                0,
                0,
                frameWidth,
                frameHeight);

        RectF whereToDraw = new RectF(
                slimeXPosition,                0,
                slimeXPosition + frameWidth,
                frameHeight);

        public float getX()
        {
            return slimeXPosition;
        }

        public GameView(Context context) {
            super(context);

            ourHolder = getHolder();
            paint = new Paint();

            chest1 = new RectF(0,0,00,0);
            chest2 = new RectF(50,50,50,50);

            //paint.setColor(blue);

            //Canvas.drawRect(chest1, paint);

            //paint.setColor(blue);

            //Canvas.drawRect(chest2, paint);

            // Load Bob from his .png file
            bitmapSlime = BitmapFactory.decodeResource(this.getResources(), R.drawable.slime);
            bitmapSlime = Bitmap.createScaledBitmap(bitmapSlime,
                    frameWidth * frameCount,
                    frameHeight,
                    false);

        }

        public void changeCanvas(int num)
        {
            colNum = num;
        }

        @Override
        public void run() {
            while (playing) {

                long startFrameTime = System.currentTimeMillis();

                update();

                draw();

                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame >= 1) {
                    fps = 1000 / timeThisFrame;
                }

            }

        }

        public void update()
        {
            if(isMoving){
                slimeXPosition = slimeXPosition + (walkSpeedPerSecond / fps);
            }

        }

        public void getCurrentFrame(){

            long time  = System.currentTimeMillis();
            if(isMoving) {
                if ( time > lastFrameChangeTime + frameLengthInMilliseconds) {
                    lastFrameChangeTime = time;
                    currentFrame++;
                    if (currentFrame >= frameCount) {

                        currentFrame = 0;
                    }
                }
            }
            frameToDraw.left = currentFrame * frameWidth;
            frameToDraw.right = frameToDraw.left + frameWidth;

        }

        public void draw()
        {
            if (ourHolder.getSurface().isValid())
            {
                canvas = ourHolder.lockCanvas();

                final Handler handler = new Handler(Looper.getMainLooper());

                if(nameAsked == true)
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("Your Name");
                    final EditText input = new EditText(MainActivity.this);
                    alert.setView(input);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            playerName = input.getText().toString();
                            //call a unction/void which is using the public var playerName
                        }
                    });
                    alert.show();
                    nameAsked = false;
                }

                canvas.drawColor(white);
                //canvas.drawColor(colors[colNum]);

                paint.setColor(Color.argb(255,  249, 129, 0));

                paint.setTextSize(45);

                canvas.drawText("FPS:" + fps, 20, 40, paint);

                whereToDraw.set((int)slimeXPosition,
                        0,
                        (int)slimeXPosition + frameWidth,
                        frameHeight);

                getCurrentFrame();

                canvas.drawBitmap(bitmapSlime,
                        frameToDraw,
                        whereToDraw, paint);

                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:
                    isMoving = true;
                    break;

                case MotionEvent.ACTION_UP:
                    isMoving = false;
                    break;
            }
            return true;
        }

        public String popUp()
        {
            nameAsked = true;
            return playerName;
        }

        public void wonGame()
        {

        }

        public void releaseSpiders()
        {
            spidersReleased = true;
        }

        public void releaseSnakes() { snakesReleased = true;}

        public void releaseSkeletons() { skeletonsReleased = true;}
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        story.playGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

}