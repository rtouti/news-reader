package com.reddit.client.redditclient2.controllers.listeners;

import android.content.Intent;
import android.view.View;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.activities.SplashScreen;

/**
 * Created by raouf on 17-04-11.
 */

public class MainMenuOnClickListener implements View.OnClickListener {
    private SplashScreen activity;

    public MainMenuOnClickListener(SplashScreen activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.news_button){
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        }
    }

}