package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class feedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//
                Intent intent =new Intent(feedbackActivity.this, MainActivity.class);
                startActivity(intent);


                finish();

            }
        },6000);
    }
}