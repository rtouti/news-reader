package com.reddit.client.redditclient2.controllers.listeners;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.controllers.activities.AccountActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.async.AccountFetchingTask;

/**
 * Created by raouf on 17-03-20.
 */

public class DrawerOnItemClickListener implements AdapterView.OnItemClickListener {
    private MainActivity activity;
    private String[] drawerItemsStrings;

    public DrawerOnItemClickListener(MainActivity activity, String[] drawerItemsStrings){
        this.activity = activity;
        this.drawerItemsStrings = drawerItemsStrings;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            if(!RedditClient.CONNECTED){
                String url = RedditClient.getAuthorizationURL(true, "submit", "identity", "mysubreddits");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(intent);
            }
            else {
                AccountFetchingTask task = new AccountFetchingTask(activity);
                task.execute();
            }
        }
        else {
            activity.changeCurrentSubreddit(drawerItemsStrings[position]);
        }
    }

}
