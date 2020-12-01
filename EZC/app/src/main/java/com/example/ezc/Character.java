package com.example.ezc;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;

import static com.example.ezc.PlayView.screenRatioX;
import static com.example.ezc.PlayView.screenRatioY;

public class Character {

    Bitmap character, dead;
    int cwidth, cheight;
    int x,y;
    private PlayView playView;



    Character (PlayView playView, int screenY, Resources res) {

        this.playView = playView;

        character = BitmapFactory.decodeResource(res, R.drawable.chicken);

        cwidth = character.getWidth()/3;
        cheight = character.getHeight()/3;

        //cwidth = (int) ( cwidth * screenRatioX);
        //cheight = (int) ( cheight * screenRatioY);
        //cwidth= 220;
        //cheight= 220;

        character = Bitmap.createScaledBitmap(character,cwidth,cheight,false);
        //character = Bitmap.compress(Bitmap.CompressFormat.PNG, 100, character);

        x = 64* (int) screenRatioX;
        y = screenY  /2 + 60;

    }

    Rect getCollisionShape() {

        return new Rect(x+40,y+20,x+cwidth-40, y+cheight-20);
    }
}
