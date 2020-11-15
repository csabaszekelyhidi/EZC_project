package com.example.ezc;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.ezc.PlayView.screenRatioX;
import static com.example.ezc.PlayView.screenRatioY;

public class Character {

    Bitmap character, dead;
    private int cwidth, cheight;
    int x,y;
    private PlayView playView;



    Character (PlayView playView, int screenY, Resources res) {

        this.playView = playView;

        character = BitmapFactory.decodeResource(res, R.drawable.circle);

        cwidth = character.getWidth();
        cheight = character.getHeight();

        cwidth = (int) ( cwidth * screenRatioX);
        cheight = (int) ( cheight * screenRatioY);
        cwidth= 200;
        cheight= 200;

        character = Bitmap.createScaledBitmap(character,cwidth,cheight,false);
        //character = Bitmap.compress(Bitmap.CompressFormat.PNG, 100, character);

        x = 64* (int) screenRatioX;
        y = screenY /2;

    }

    Rect getCollisionShape() {

        return new Rect(x,y,x+cwidth, y+cheight);
    }
}
