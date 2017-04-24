package com.reddit.client.redditclient2.controllers.listeners;

import android.content.DialogInterface;

import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;

/**
 * Created by raouf on 17-04-21.
 */

public class OnSortingMultiChoiceClickListener implements DialogInterface.OnMultiChoiceClickListener {
    private MainActivity activity;

    public OnSortingMultiChoiceClickListener(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        activity.changeCurrentSubreddit(
                activity.getCurrentSubreddit(),
                PostFetchingTask.CURRENT_SORTING,
                activity.getSortingItems()[which]
        );
    }

}