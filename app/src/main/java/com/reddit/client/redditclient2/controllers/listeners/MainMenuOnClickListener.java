package com.reddit.client.redditclient2.controllers.listeners;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.activities.SplashScreen;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

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
            if(HttpRequestUtil.isConnected(activity)) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }
            else Toast.makeText(activity, R.string.failed_connection, Toast.LENGTH_LONG).show();
        }
    }

}