package com.reddit.client.redditclient2.controllers.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.controllers.listeners.MainMenuOnClickListener;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();


        final ImageView logo = (ImageView)findViewById(R.id.logo);

        Animation in  = new AlphaAnimation(0, 1);
        Animation out  = new AlphaAnimation(1, 0);
        in.setDuration(2000);
        out.setDuration(3000);
        out.setStartOffset(4000);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setAlpha(0.0f);

                if(HttpRequestUtil.isConnected(SplashScreen.this)) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else Toast.makeText(SplashScreen.this, R.string.failed_connection, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(in);
        animation.addAnimation(out);
        animation.setRepeatCount(1);
        logo.setAnimation(animation);
    }
}
