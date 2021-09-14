package com.example.chatspatial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(7000);

                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent welcomeIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(welcomeIntent);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}