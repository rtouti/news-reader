package com.reddit.client.redditclient2.controllers.listeners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

/**
 * Created by raouf on 17-03-20.
 */

public class ArticlesOnItemClickListener implements AdapterView.OnItemClickListener {
    private MainActivity activity;

    public ArticlesOnItemClickListener(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(HttpRequestUtil.isConnected(activity)) {
            Link link = activity.getLinks().get(position);

            Intent intent = new Intent(activity.getApplicationContext(), ArticlesActivity.class);
            intent.putExtra("link", link);
            activity.startActivity(intent);
            //activity.findViewById(R)
        }
        
        else Toast.makeText(activity, R.string.failed_connection, Toast.LENGTH_LONG).show();

    }

}
