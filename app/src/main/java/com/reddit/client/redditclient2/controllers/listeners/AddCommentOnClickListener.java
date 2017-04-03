package com.reddit.client.redditclient2.controllers.listeners;

import android.view.View;

import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;

/**
 * Created by raouf on 17-03-29.
 */

public class AddCommentOnClickListener implements View.OnClickListener {
    private ArticlesActivity activity;

    public AddCommentOnClickListener(ArticlesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

    }

}
