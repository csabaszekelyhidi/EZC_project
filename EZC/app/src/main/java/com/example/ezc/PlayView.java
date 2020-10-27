package com.example.ezc;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.SurfaceView;

public class PlayView extends SurfaceView implements Runnable {

    private Thread thread;
    private Background background1,background2;
    private Character character;
    private boolean isPlaying, isGameOver = false;
    private int width, height, screenX, screenY;
    private double speed=1;
    public static float screenRatioX, screenRatioY;
    private PlayActivity playActivity;
    private Obstacles obstacles;
    private Paint paint;

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
    speed = speed+0.001;
    background1.x -= 20*screenRatioX+speed;
    background2.x -= 20*screenRatioX+speed;
    if(background1.x + background1.background.getWidth() <0){
        background1.x = screenX;
    }
    if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
    }

/*        if (Rect.intersects(obstacles.getCollisionShape(), character.getCollisionShape())) {

            isGameOver = true;
            //go to the result activity if need
        }*/

    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);

            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(character.character,character.x,character.y,paint);

            //for score
            //canvas.drawText(score + "", screenX / 2f, 164, paint);

            getHolder().unlockCanvasAndPost(canvas);
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
}
