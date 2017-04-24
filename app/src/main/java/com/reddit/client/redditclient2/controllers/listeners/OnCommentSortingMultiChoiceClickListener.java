package com.reddit.client.redditclient2.controllers.listeners;

import android.content.DialogInterface;

import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.async.CommentsFetchingTask;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;

/**
 * Created by raouf on 17-04-21.
 */

public class OnCommentSortingMultiChoiceClickListener implements DialogInterface.OnClickListener {
    private ArticlesActivity activity;

    public OnCommentSortingMultiChoiceClickListener(ArticlesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        activity.updateComment(activity.getSortingItems()[which]);
    }
}