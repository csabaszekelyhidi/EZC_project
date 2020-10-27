package com.example.ezc;


import android.graphics.Point;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class PlayActivity extends AppCompatActivity {


    private PlayView playView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        playView = new PlayView(this, point.x, point.y);
        setContentView(playView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        playView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playView.resume();
    }





}