package com.ktchen.cookapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

/// this is our first screen we show to users
// Another comment fro practice
// Jess made a comment here.
// Made a comment here
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ActivityInfo","SplashActivity created");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);  // Hide the title
        //Objects.requireNonNull(getSupportActionBar()).hide();   // Hide the title bar
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN); // Enable ful screen mode
        //setContentView(R.layout.activity_splash);


        //new Handler().postDelayed(new Runnable() {
        //    @Override
        //    public void run() {
        //        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        //        startActivity(intent);
        //        finish();
        //    }
        //}, 2000);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
