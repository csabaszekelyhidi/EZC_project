package com.example.ezc;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    Bitmap background;
    int x=0,y=0;

    Background (int screenX, int screenY, Resources resources){



        background = BitmapFactory.decodeResource(resources, R.drawable.bg_ezc);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);


    }
}
