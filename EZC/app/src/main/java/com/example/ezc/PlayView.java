package com.example.ezc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Log;
import android.view.SurfaceView;

public class PlayView extends SurfaceView implements Runnable {

    private Thread thread;
    private Background background1,background2;
    private Character character;
    private boolean isPlaying, isJump = false, isGoDown = false, isGameOver = false;
    private int width, height, screenX, screenY;
    private int points = 0;
    private double speed=1, jumpspeed = -30;
    public static float screenRatioX, screenRatioY;
    private PlayActivity playActivity;
    private Obstacles obstacles;
    private Paint paint;
    private Canvas canvas;

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

/*      forScore
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);*/

        obstacles = new Obstacles(getResources());
        background2.x = screenX;
        character.x = character.x +200;


    }

    @Override
    public void run() {

        while(isPlaying) {
            update();
            draw();
            sleep();
        }

    }


    private void update(){
        points++;
    //speed = speed+0.001;
    //background1.x -= 10*screenRatioX-speed;
    //background2.x -= 10*screenRatioX-speed;
    background1.x -= 8;
    background2.x -= 8;

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

            canvas.drawCircle(background2.x, character.y, 80, paint);

            paint.setTextSize(48f);
            canvas.drawText("points: "+points,screenX - 400, 100, paint);


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
}
