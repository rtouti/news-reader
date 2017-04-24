package com.reddit.client.redditclient2.controllers.activities;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.controllers.listeners.MainMenuOnClickListener;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        Button news_button = (Button)findViewById(R.id.news_button);
        news_button.setOnClickListener(new MainMenuOnClickListener(this));

        loop();
    }

    private void loop(){
        ImageView logo = (ImageView)findViewById(R.id.logo);
        int n = 1000;
        for(int i = 0; i < n; i++){
            SystemClock.sleep(3);
            logo.setAlpha((float)i/n);
        }
    }
}
