package com.example.ezc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceView;

import java.util.concurrent.ThreadLocalRandom;

public class PlayView extends SurfaceView implements Runnable {

    private Thread thread;
    private Background background1,background2;
    private Character character;
    private boolean isPlaying, isJump = false, isGoDown = false, isGameOver = false;
    private int width, height, screenX, screenY;
    private int points = 0;
    private double speed=0, jumpspeed = -30;
    public static float screenRatioX, screenRatioY;
    private PlayActivity playActivity;
    private Obstacles obstacles;
    private Paint paint;
    private Canvas canvas;

    private int o1X=-50, o1Y=-50, o2X=-50, o2Y=-50;

    private static final String NIMLOG = "NimLog";

    public PlayView(PlayActivity playActivity, int screenX, int screenY) {
        super(playActivity);

        this.playActivity = playActivity;

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX,screenY,getResources());
        background2 = new Background(screenX,screenY,getResources());
        character = new Character(this,screenY,getResources());
        paint = new Paint();
        paint.setColor(Color.RED);

/*      forScore
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);*/

        obstacles = new Obstacles(getResources());
        background2.x = screenX;
        character.x = character.x +200;


    }

    @Override
    public void run() {

        while(isPlaying)
        {
            if ( isGameOver() == false )
            {
                update();
                draw();
                sleep();
            }

        }

    }


    private void update()
    {
        points++;
        speed = (int) points / 500;
    //speed = speed+0.001;
    //background1.x -= 10*screenRatioX-speed;
    //background2.x -= 10*screenRatioX-speed;
    background1.x -= 8+speed;
    background2.x -= 8+speed;

    if ( o1X > -50 )
    {
        o1X -= 12+speed*2;
    }
    else {
        generateObject(1);
    }
    if ( o2X > -50 )
    {
        o2X -= 12+speed*2;
    }
    else {
        generateObject(2);
    }

    if(background1.x + background1.background.getWidth() <0){
        background1.x = screenX;
    }
    if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
    }

    // JUMPING
    if (isJump == true)
    {
        //Log.d(NIMLOG,"NimLOG: isJump = true : "+jumpspeed);
        if ( jumpspeed <= 30)
        {
            character.y += jumpspeed;
            jumpspeed++;
        }
        else
        {
            isJump = false;
            jumpspeed = -30;
            //Log.d(NIMLOG,"NimLOG: isJump = false : "+jumpspeed);
        }
    }

        // GOING DOWN
        if (isGoDown == true)
        {
            //Log.d(NIMLOG,"NimLOG: isJump = true : "+jumpspeed);
            if ( jumpspeed <= 30)
            {
                if ( jumpspeed % 2 == 0 ) {

                    character.y -= jumpspeed;
                }
                jumpspeed++;
            }
            else
            {
                isGoDown = false;
                jumpspeed = -30;
                //Log.d(NIMLOG,"NimLOG: isJump = false : "+jumpspeed);
            }
        }

/*        if (Rect.intersects(obstacles.getCollisionShape(), character.getCollisionShape())) {

            isGameOver = true;
            //go to the result activity if need
        }*/

    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(character.character,character.x,character.y,paint);

            canvas.drawCircle(o1X, o1Y, 80, paint);
            canvas.drawCircle(o2X, o2Y, 50, paint);

            paint.setTextSize(48f);
            canvas.drawText("points: "+points,screenX - 400, 100, paint);
            canvas.drawText("level: "+speed,200, 100, paint);


            //for score
            //canvas.drawText(score + "", screenX / 2f, 164, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    public void jump()
    {
        if (isGoDown == false) {
            isJump = true;
        }
        Log.d(NIMLOG,"NimLOG: jump() called");
    }
    public void goDown()
    {
        if (isJump == false) {
            isGoDown = true;
        }
        Log.d(NIMLOG,"NimLOG: goDown() called");
    }

    public void generateObject( int ball )
    {
        if ( ball == 1 ) {
            o1X = screenX + ThreadLocalRandom.current().nextInt(0,screenX/2);
            o1Y = screenY / 2 + ThreadLocalRandom.current().nextInt(-250,250);
        }
        else if ( ball == 2 ) {
            o2X = o1X + ThreadLocalRandom.current().nextInt(screenX/2,screenX);
            o2Y = screenY / 2 + ThreadLocalRandom.current().nextInt(-250,250);
        }

    }

    private void sleep(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void resume (){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public boolean isGameOver()
    {
        int sizeX = character.x + character.cwidth;
        int sizeY = character.y + character.cwidth;
        if (character.x < o1X+80 && sizeX > o1X-80 && character.y < o1Y+80 && sizeY > o1Y-80 )
        {
            isGameOver = true;
        }
        if (character.x < o2X+50 && sizeX > o2X-50 && character.y < o2Y+50 && sizeY > o2Y-50 )
        {
            isGameOver = true;
        }

        return isGameOver;
    }
}
