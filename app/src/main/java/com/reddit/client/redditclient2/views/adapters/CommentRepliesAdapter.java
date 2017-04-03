package com.reddit.client.redditclient2.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Comment;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;

import java.util.ArrayList;

/**
 * Created by raouf on 17-03-28.
 */

public class CommentRepliesAdapter extends BaseAdapter {
    private ArticlesActivity activity;
    private Comment comment;
    private ArrayList<Comment> comments;

    public CommentRepliesAdapter(ArticlesActivity activity, Comment comment){
        this.activity = activity;
        this.comment = comment;
        this.comments = comment.replies();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.comment_list_item_layout, parent, false);
        }

        Comment comment = comments.get(position);
        TextView body = (TextView)convertView.findViewById(R.id.comment_body);
        TextView author = (TextView)convertView.findViewById(R.id.comment_author);
        TextView score = (TextView)convertView.findViewById(R.id.comment_score);
        body.setText(comment.body);
        author.setText(comment.author);
        score.setText(""+comment.score);

        return convertView;
    }
}
