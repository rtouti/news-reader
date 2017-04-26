package com.reddit.client.redditclient2.controllers.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Comment;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.http.HttpRequestUtil;
import com.reddit.client.redditclient2.views.adapters.CommentRepliesAdapter;

/**
 * Created by raouf on 17-03-28.
 */

public class CommentsOnItemClickListener implements AdapterView.OnItemClickListener {
    private ArticlesActivity activity;

    public CommentsOnItemClickListener(ArticlesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(HttpRequestUtil.isConnected(activity)) {
            Comment comment = activity.getAdapter().getComments().get(position);

            ListView replies = (ListView) view.findViewById(R.id.replies);
            replies.setAdapter(new CommentRepliesAdapter(activity, comment));
            replies.setOnItemClickListener(new CommentsOnItemClickListener(activity));
        }
        else Toast.makeText(activity, R.string.failed_connection, Toast.LENGTH_LONG).show();

    }

}