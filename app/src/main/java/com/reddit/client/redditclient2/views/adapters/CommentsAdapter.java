package com.reddit.client.redditclient2.views.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Comment;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.async.CommentsFetchingTask;
import com.reddit.client.redditclient2.controllers.listeners.AddCommentOnClickListener;
import com.reddit.client.redditclient2.utils.TextSizes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by raouf on 17-03-23.
 */

public class CommentsAdapter extends BaseAdapter {
    private ArticlesActivity activity;
    private Link link;
    private ArrayList<Comment> comments;
    private boolean commentsLoaded = false;
    private int count = 0;

    public CommentsAdapter(ArticlesActivity activity){
        this.activity = activity;
        this.link = activity.getLink();

        CommentsFetchingTask task = new CommentsFetchingTask(this);
        task.execute();
    }

    @Override
    public int getCount() {
        return count+1;
    }

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //On a du sacrifier la réutilisation des view pour pouvoir utiliser le
    //"first_article_list_item_layout" comme premier élément dans le list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //if(convertView == null){
            if(position == 0){
                convertView = activity.getLayoutInflater().inflate(R.layout.first_article_list_item_layout, parent, false);
            }
            else {
                convertView = activity.getLayoutInflater().inflate(R.layout.comment_list_item_layout, parent, false);
            }
        //}

        if(position == 0){
            TextView title = (TextView)convertView.findViewById(R.id.article_title);
            TextView author = (TextView)convertView.findViewById(R.id.article_author);
            TextView score = (TextView)convertView.findViewById(R.id.article_score);
            title.setText(link.title);
            author.setText(link.author);
            score.setText(""+link.score);
            title.setTextSize(TextSizes.FIRST_COMMENT_TITLES_SIZE);
            author.setTextSize(TextSizes.FIRST_COMMENT_AUTHOR_SIZE);
            score.setTextSize(TextSizes.FIRST_COMMENT_SCORE_SIZE);

            ImageButton add_comment = (ImageButton)convertView.findViewById(R.id.add_comment);
            add_comment.setOnClickListener(new AddCommentOnClickListener(activity));

            String path = link.highResPicture != null ? link.highResPicture : "";
            ImageView image = (ImageView)convertView.findViewById(R.id.article_image);
            if(!path.equals("") && !path.equals("self") && !path.equals("image") && !path.equals("default")){
                Picasso.with(activity.getApplicationContext())
                        .load(path)
                        .into(image);
            }
            else {
                image.setVisibility(View.GONE);
            }
        }
        else {
            Log.i("DEBUG", ""+position);
            Comment comment = comments.get(position-1);

            TextView body = (TextView)convertView.findViewById(R.id.comment_body);
            TextView author = (TextView)convertView.findViewById(R.id.comment_author);
            TextView score = (TextView)convertView.findViewById(R.id.comment_score);
            body.setText(comment.body);
            author.setText(comment.author);
            score.setText(""+comment.score);
            body.setTextSize(TextSizes.COMMENTS_BODY_SIZE);
            author.setTextSize(TextSizes.COMMENTS_AUTHOR_SIZE);
            score.setTextSize(TextSizes.COMMENTS_SCORE_SIZE);
        }

        return convertView;
    }

    public Link getLink(){
        return link;
    }

    public ArrayList<Comment> getComments(){
        return comments;
    }

    public void setComments(ArrayList<Comment> comments){
        this.comments = comments;
    }

    public void setCommentsLoaded(boolean commentsLoaded){
        this.commentsLoaded = commentsLoaded;
    }
}
