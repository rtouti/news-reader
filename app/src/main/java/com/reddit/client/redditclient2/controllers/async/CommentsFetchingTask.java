package com.reddit.client.redditclient2.controllers.async;

import android.os.AsyncTask;

import com.reddit.client.redditclient2.api.things.Comment;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.views.adapters.CommentsAdapter;

import java.util.ArrayList;

/**
 * Created by raouf on 17-03-23.
 */

public class CommentsFetchingTask extends AsyncTask<Object, Object, ArrayList<Comment>> {
    public static String CURRENT_SORTING = "";
    public static String SORTING_APPRECIATED = "";
    public static String SORTING_NEW = "new";
    public static String SORTING_CONTROVERSIAL = "controversial";
    public static String SORTING_OLD = "old";

    private CommentsAdapter adapter;

    public CommentsFetchingTask(CommentsAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<Comment> doInBackground(Object... params) {
        ArrayList<Comment> comments = adapter.getLink().comments();

        return comments;
    }

    @Override
    protected void onPostExecute(ArrayList<Comment> comments) {
        adapter.setComments(comments);
        adapter.setCommentsLoaded(true);
        adapter.setCount(comments.size());
        adapter.notifyDataSetChanged();
    }
}
