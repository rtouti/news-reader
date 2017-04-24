package com.reddit.client.redditclient2.controllers.async;

import android.os.AsyncTask;

import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.api.things.Thing;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;

/**
 * Created by raouf on 17-04-13.
 */

public class AddCommentTask extends AsyncTask<Object, Object, Object> {
    private ArticlesActivity activity;

    public AddCommentTask(ArticlesActivity activity){
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Object... params) {
        RedditClient client = new RedditClient();
        client.reply((String)params[0], (Thing)params[1]);

        return null;
    }

}