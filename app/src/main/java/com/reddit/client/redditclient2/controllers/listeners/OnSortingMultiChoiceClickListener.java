package com.reddit.client.redditclient2.controllers.listeners;

import android.content.DialogInterface;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

/**
 * Created by raouf on 17-04-21.
 */

public class OnSortingMultiChoiceClickListener implements DialogInterface.OnClickListener {
    private MainActivity activity;

    public OnSortingMultiChoiceClickListener(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(HttpRequestUtil.isConnected(activity)) {
            activity.changeCurrentSubreddit(
                    activity.getCurrentSubreddit(),
                    PostFetchingTask.CURRENT_SORTING,
                    activity.getSortingItems()[which]
            );

        }
        else Toast.makeText(activity, R.string.failed_connection, Toast.LENGTH_LONG).show();

        dialog.dismiss();

    }
}