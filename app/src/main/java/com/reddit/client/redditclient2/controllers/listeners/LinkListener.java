package com.reddit.client.redditclient2.controllers.listeners;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;

/**
 * Created by olivier on 17-04-24.
 */

public class LinkListener implements View.OnClickListener {
    private ArticlesActivity activity;

    public LinkListener(ArticlesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

        Uri uri = Uri.parse(activity.getLink().url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);

    }
}
