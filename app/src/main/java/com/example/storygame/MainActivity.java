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
import android.widget.TextView;

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
    Drawable rock1;
    Drawable rock2;
    Drawable skulls;
    Drawable grave;

    public TextView endText;
    public TextView coors;

    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int UP = 3;
    public final int DOWN = 4;

    int screenX;
    int screenY;

    int curX = 0;
    int curY = 0;

    public void Sprite(GameView gameView, Bitmap bmp) {

        this.gameView=gameView;

        this.bmp=bmp;

    }

    public void enemeyWave(Drawable enemy)
    {
        Drawable curEnemy = enemy;

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
        rock1 = getResources().getDrawable(R.drawable.rocks);
        rock2 = getResources().getDrawable(R.drawable.rock2);
        grave = getResources().getDrawable(R.drawable.grave);
        skulls = getResources().getDrawable(R.drawable.skulls);

        coors = (TextView) findViewById(R.id.coor);

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
        Bitmap bitmapRed;
        Bitmap bitmapBlue;
        Bitmap bitmapSpider;
        Bitmap bitmapSkeleton;
        Bitmap bitmapSnake;
        Bitmap bitmapRock1;
        Bitmap bitmapRock2;
        Bitmap bitmapGrave;
        Bitmap bitmapSkulls;

        boolean isMovingLeft = false;
        boolean isMovingRight = false;
        boolean isMovingUp = false;
        boolean isMovingDown = false;
        boolean isMoving = false;

        float walkSpeedPerSecond = 300;

        float slimeXPosition = 10;
        float slimeYPosition = 10;

        private int frameWidth = 200;
        private int frameHeight = 100;

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


        private Rect frameToDraw = new Rect(0, 0, frameWidth, frameHeight);

        RectF whereToDraw = new RectF(slimeXPosition,slimeYPosition, slimeXPosition + frameWidth, slimeYPosition+frameHeight);

        public float getX()
        {
            return slimeXPosition;
        }

        public float getY()
        {
            return slimeYPosition;
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
            bitmapSlime = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
            bitmapSlime = Bitmap.createScaledBitmap(bitmapSlime, frameWidth * frameCount, frameHeight, false);

            bitmapSpider = BitmapFactory.decodeResource(this.getResources(), R.drawable.spider);
            bitmapSpider = Bitmap.createScaledBitmap(bitmapSpider, 100, 50, false);

            bitmapRed = BitmapFactory.decodeResource(this.getResources(), R.drawable.red);
            bitmapRed = Bitmap.createScaledBitmap(bitmapRed, 40 * frameCount, 140, false);

            bitmapBlue = BitmapFactory.decodeResource(this.getResources(), R.drawable.blue);
            bitmapBlue = Bitmap.createScaledBitmap(bitmapBlue, 40 * frameCount, 140, false);

            bitmapRock1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.rocks);
            bitmapRock1 = Bitmap.createScaledBitmap(bitmapRock1, 40 * frameCount, 140, false);

            bitmapRock2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.rock2);
            bitmapRock2 = Bitmap.createScaledBitmap(bitmapRock2, 40 * frameCount, 140, false);

            bitmapSkulls = BitmapFactory.decodeResource(this.getResources(), R.drawable.skulls);
            bitmapSkulls = Bitmap.createScaledBitmap(bitmapSkulls, 40 * frameCount, 140, false);

            bitmapGrave = BitmapFactory.decodeResource(this.getResources(), R.drawable.grave);
            bitmapGrave = Bitmap.createScaledBitmap(bitmapGrave, 40 * frameCount, 140, false);


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
            if(isMovingRight){
                slimeXPosition = slimeXPosition + (walkSpeedPerSecond / fps);
            }
            if(isMovingLeft){
                slimeXPosition = slimeXPosition - (walkSpeedPerSecond / fps);
            }
            if(isMovingUp){
                slimeYPosition = slimeYPosition - (walkSpeedPerSecond / fps);
            }
            if(isMovingDown){
                slimeYPosition = slimeYPosition + (walkSpeedPerSecond / fps);
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
                canvas.drawBitmap(bitmapSpider, 500, 500, paint);
                if(spidersReleased == true)
                {
                    canvas.drawBitmap(bitmapSpider, 500, 500, paint);
                }
                if(snakesReleased == true)
                {

                }
                if(skeletonsReleased == true)
                {

                }

                canvas.drawColor(Color.DKGRAY);

                screenX = canvas.getWidth();
                screenY = canvas.getHeight();
                //canvas.drawColor(colors[colNum]);

                paint.setColor(Color.argb(255,  249, 129, 0));

                paint.setTextSize(45);

                canvas.drawText("X " + screenX + ", Y " + screenY, 20, 40, paint);
                canvas.drawText("CurX " + curX + ", CurY " + curY, 20, 80, paint);

                whereToDraw.set((int)slimeXPosition, slimeYPosition, (int)slimeXPosition + frameWidth, (int)slimeYPosition+frameHeight);

                getCurrentFrame();

                canvas.drawBitmap(bitmapSlime, frameToDraw, whereToDraw, paint);
                canvas.drawBitmap(bitmapRed, 30, 1400, paint);
                canvas.drawBitmap(bitmapBlue, 850, 1400, paint);
                canvas.drawBitmap(bitmapRock1, 750, 300, paint);
                canvas.drawBitmap(bitmapRock2, 160, 600, paint);
                canvas.drawBitmap(bitmapSkulls, 400, 1100, paint);
                canvas.drawBitmap(bitmapGrave, 525, 1400, paint);
                //canvas.drawBitmap(bitmapSlime, null, new RectF(0, 0, screenX/2, screenY/2), null);

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

        public void spiderWave()
        {

        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            float x = motionEvent.getX();

            float y = motionEvent.getY();

            String coorNums = (int)x + ", " + (int)y;
            //coors.setText(coorNums);

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:
                    curX = (int)motionEvent.getX();
                    curY = (int)motionEvent.getY();
                    isMoving = true;
                    if(motionEvent.getX() > 540)
                    {
                        isMovingRight = true;
                    }
                    if(motionEvent.getX() <= 540)
                    {
                        isMovingLeft = true;
                    }
                    if(motionEvent.getY() < 768)
                    {
                        isMovingUp = true;
                    }
                    if(motionEvent.getY() >= 768)
                    {
                        isMovingDown = true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    isMoving = false;
                    isMovingDown = false;
                    isMovingUp = false;
                    isMovingLeft = false;
                    isMovingRight = false;
                    break;
            }
            return true;
        }

        public String popUp()
        {
            nameAsked = true;
            return playerName;
        }

        public void wonGame(int num)
        {
            endText.setText("You win!");
            final Handler handler = new Handler();
            if(num == 0)
            {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endText.setText("But you get no treasure");
                    }
                }, 5000);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endText.setText("Bad ending! (I guess)");
                    }
                }, 5000);
            }
            else if(num == 1)
            {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endText.setText("You found the treasure!");
                    }
                }, 5000);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endText.setText("Good ending!");
                    }
                }, 5000);
            }
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