package com.example.ezc;

import android.graphics.*;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PlayView extends SurfaceView implements Runnable {

    private Thread thread;
    private Background background1,background2;
    private Character character;
    private boolean isPlaying, isJump = false, isGoDown = false, isGameOver = false, goUp = false;
    private int width, height, screenX, screenY;
    private int points = 0;
    private double speed=0, jumpspeed = -30;
    public static float screenRatioX, screenRatioY;
    private PlayActivity playActivity;
    private Obstacles obstacle1, obstacle2;
    private Paint paint;
    private Canvas canvas;

    private double inAir;
    private int inAir_speed, r = 600;

    private int o1X=-50, o1Y=-50, o2X=-50, o2Y=-50;

    private static final String NIMLOG = "NimLog";

    public PlayView(PlayActivity playActivity, int screenX, int screenY) {
        super(playActivity);

        this.playActivity = playActivity;

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(5000,1000,getResources());
        background2 = new Background(5000,1000,getResources());
        character = new Character(this,screenY,getResources());
        paint = new Paint();
        paint.setColor(Color.RED);

/*      forScore
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);*/

        obstacle1 = new Obstacles(getResources(), 1);
        obstacle2 = new Obstacles(getResources(), 2);

        background2.x = background2.background.getWidth();
        character.x = character.x +200;


    }

    @Override
    public void run() {

        while(isPlaying)
        {
            if ( isGameOver == false )
            {
                update();
                draw();
                sleep();
            }
            else {
                isPlaying = false;
            }

        }
        if ( isPlaying == false )
        {
            drawGameOver();
        }

    }


    private void update()
    {
        points++;
        speed = (int) points / 500;
        background1.x -= 12+speed;
        background2.x -= 12+speed;

        if ( obstacle1.x + obstacle1.cwidth <= 0 )
        {
            obstacle1.x = screenX + ThreadLocalRandom.current().nextInt(0,screenX);
            switch (ThreadLocalRandom.current().nextInt(0,1000) % 3 )
            {
                case 0:
                case 1: obstacle1.y = screenY / 2 + 80; break;
                case 2: obstacle1.y = screenY / 2 + 320; break;
            }
        }
        else
        {
            obstacle1.x -= 12+speed;
        }
        if ( obstacle2.x + obstacle2.cwidth <= 0 )
        {
            obstacle2.x = screenX + ThreadLocalRandom.current().nextInt(0,screenX);
            obstacle2.y = screenY / 2 -
                    ThreadLocalRandom.current().nextInt(100-((int)speed*3),screenY/2-100-((int)speed*10));
        }
        else
        {
            obstacle2.x -= 14+speed;
        }



        if(background1.x + background1.background.getWidth() <= 0)
        {
            background1.x = background2.x + background2.background.getWidth();
        }
        if (background2.x + background2.background.getWidth() <= 0)
        {
            background2.x = background1.x + background1.background.getWidth();
        }

    // JUMPING
    if (isJump == true)
    {
        if (inAir >= -400 && inAir <= 400)
        {
            character.y = (screenY/2 + 60 +1341) - (int) Math.sqrt((r*r)-(inAir*inAir))*3;
            inAir += inAir_speed;

            //Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
        }
        else
        {
            character.y = screenY/2 + 60;
            //Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
            isJump = false;
        }

    }
        // GOING DOWN
        /*if (isGoDown == true)
        {
            if (inAir >= -400 && inAir < 0)
            {
                character.y = (screenY/2 + 60 - 670) + (int) (Math.sqrt((r*r)-(inAir*inAir))*(1.5));
                inAir += inAir_speed;

                Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
            } else
            {
                character.y = screenY/2 + 60;
                //Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
                isGoDown = false;
            }
        }*/
        // GOING DOWN
        if (isGoDown == true && goUp == false)
        {
            if (inAir >= -400 && inAir < 0)
            {
                character.y = (screenY/2 + 60 - 670) + (int) (Math.sqrt((r*r)-(inAir*inAir))*(1.5));
                if ( inAir + inAir_speed <= 0 )
                {
                    inAir += inAir_speed;
                } else
                {
                    inAir = 0;
                }
                Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
            }
        } else if (isGoDown == true && goUp == true)
        {
            if (inAir >= 0 && inAir < 400)
            {
                character.y = (screenY/2 + 60 - 670) + (int) (Math.sqrt((r*r)-(inAir*inAir))*(1.5));
                inAir += inAir_speed;
                Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
            } else
            {
                character.y = screenY/2 + 60;
                //Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
                isGoDown = false;
                goUp = false;
            }
            // GOING UP
            /*
            if (inAir >= -400 && inAir < 0)
            {
                character.y = (screenY/2 + 60 - 670) + (int) (Math.sqrt((r*r)-(inAir*inAir))*(1.5));
                inAir += inAir_speed;

                Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
            }
            else
            {
                character.y = screenY/2 + 60;
                //Log.d(NIMLOG,"NimLOG: inAir="+inAir+" y="+character.y);
                isGoDown = false;
            }*/
        }

        if (character.getCollisionShape().intersect(obstacle1.getCollisionShape()) ||
                character.getCollisionShape().intersect(obstacle2.getCollisionShape()) )
        {
            //Log.d(NIMLOG,"NimLOG: COLLOSION ");
            isGameOver = true;
        }

    }

    private void draw()
    {
        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();

            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            canvas.drawBitmap(obstacle1.obstacle, obstacle1.x, obstacle1.y, paint);
            canvas.drawBitmap(obstacle2.obstacle, obstacle2.x, obstacle2.y, paint);

            canvas.drawBitmap(character.character,character.x,character.y,paint);
            //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.OVERLAY);



            //canvas.drawCircle(o1X, o1Y, 80, paint);
            //canvas.drawCircle(o2X, o2Y, 50, paint);

            paint.setTextSize(48f);
            canvas.drawText("Pontszám: "+points,screenX - 400, 100, paint);
            canvas.drawText("Szint: "+speed,200, 100, paint);


            //for score
            //canvas.drawText(score + "", screenX / 2f, 164, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    public void jump()
    {
        if (isGoDown == false)
        {
            isJump = true;
            inAir = -400;
            inAir_speed = (int) (12 + speed);
        }
        else if ( goUp == false )
        {
            goUp = true;
        }
        Log.d(NIMLOG,"NimLOG: jump() called");
    }
    public void goDown()
    {
        if (isJump == false) {
            isGoDown = true;
            inAir = -400;
            inAir_speed = (int) (12 + speed);
        }
        Log.d(NIMLOG,"NimLOG: goDown() called");
    }
    public void drawGameOver()
    {
        if(getHolder().getSurface().isValid())
        {
            canvas = getHolder().lockCanvas();

            Bitmap GO = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
            GO = Bitmap.createScaledBitmap(GO,GO.getWidth()/2,GO.getHeight()/2,false);
            canvas.drawBitmap(GO, screenX/2 - GO.getWidth()/2, screenY/2 - GO.getHeight()/2 , paint);

            paint.setTextSize(100f);
            paint.setColor(Color.WHITE);
            canvas.drawText(String.valueOf(points),screenX/2 - 30, screenY/ 2 + 75, paint);
            canvas.drawText(String.valueOf(speed),screenX/2 - 30, screenY/ 2 + 196, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void sleep()
    {
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

        return false;
    }

    public void draw_Game_Over()
    {
        if(getHolder().getSurface().isValid())
        {
            canvas = getHolder().lockCanvas();

            Bitmap GO = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
            GO = Bitmap.createScaledBitmap(GO,GO.getWidth()/2,GO.getHeight()/2,false);
            canvas.drawBitmap(GO, screenX/2 - GO.getWidth()/2, screenY/2 - GO.getHeight()/2 , paint);

            paint.setTextSize(100f);
            paint.setColor(Color.WHITE);
            canvas.drawText(String.valueOf(points),screenX/2 - 30, screenY/ 2 + 75, paint);
            canvas.drawText(String.valueOf(speed),screenX/2 - 30, screenY/ 2 + 196, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }
}
