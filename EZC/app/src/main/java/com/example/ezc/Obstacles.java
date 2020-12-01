package com.example.ezc;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.ezc.PlayView.screenRatioX;
import static com.example.ezc.PlayView.screenRatioY;


public class Obstacles
{
    Bitmap obstacle;
    int cwidth, cheight;
    int x = 0, y = 0;
    int speed;

    Obstacles(Resources res, int type)
    {
        if ( type == 1 )
        {
            obstacle = BitmapFactory.decodeResource(res, R.drawable.stone_gif);
        }
        else
        {
            obstacle = BitmapFactory.decodeResource(res, R.drawable.bird_gif);
        }



        //cwidth = obstacle.getWidth();
        //cheight = obstacle.getHeight();

        cwidth = obstacle.getWidth() / 4;
        cheight = obstacle.getHeight() / 4;

        obstacle = Bitmap.createScaledBitmap(obstacle,cwidth,cheight,false);
        //character = Bitmap.compress(Bitmap.CompressFormat.PNG, 100, character);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    Rect getCollisionShape() {

        return new Rect(x+10,y+10,x+cwidth-10, y+cheight-10);
    }
}



