package com.reddit.client.redditclient2.controllers.listeners;

import android.content.DialogInterface;

import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;

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
        activity.changeCurrentSubreddit(
                activity.getCurrentSubreddit(),
                PostFetchingTask.CURRENT_SORTING,
                activity.getSortingItems()[which]
        );

        dialog.dismiss();
    }
}