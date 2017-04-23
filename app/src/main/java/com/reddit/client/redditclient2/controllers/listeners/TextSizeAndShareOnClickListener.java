package com.reddit.client.redditclient2.controllers.listeners;

import android.content.Intent;
import android.view.View;

import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;

/**
 * Created by raouf on 17-04-06.
 */

public class TextSizeAndShareOnClickListener implements View.OnClickListener {
    private ArticlesActivity activity;
    Link link = new Link();
    int pos;
    public TextSizeAndShareOnClickListener(ArticlesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {


        Intent Share = new Intent(android.content.Intent.ACTION_SEND);
        Share.setType("text/plain");

       /* pos= getIntent().getIntExtra("position",0);
        String url1=lineup.children.get(pos).data.url;
        url = lineup.children.get(pos).data.thumbnail;
        stitre = lineup.children.get(pos).data.title;*/
        String stitre = "la realité";
        String url1=link.url;
        // lineup=(RootData)Share.getSerializableExtra("object");
        // Share.putExtra(android.content.Intent.EXTRA_SUBJECT,url);
        Share.putExtra(android.content.Intent.EXTRA_TEXT, stitre);
        //    try{
        activity.startActivity(Intent.createChooser(Share, "shaire"));



        //Augmenter la taille du texte
     /*   if(v.getId() == R.id.text_size_up_button){
            TextSizes.FIRST_COMMENT_TITLES_SIZE++;
            TextSizes.FIRST_COMMENT_AUTHOR_SIZE += 0.5f;
            TextSizes.FIRST_COMMENT_SCORE_SIZE += 0.5f;
            TextSizes.COMMENTS_BODY_SIZE++;
            TextSizes.COMMENTS_AUTHOR_SIZE += 0.5f;
            TextSizes.COMMENTS_SCORE_SIZE += 0.5f;

            activity.getAdapter().notifyDataSetChanged();
        }
        //Diminuer la taille du texte
        else if(v.getId() == R.id.text_size_down_button){
            TextSizes.FIRST_COMMENT_TITLES_SIZE--;
            TextSizes.FIRST_COMMENT_AUTHOR_SIZE -= 0.5f;
            TextSizes.FIRST_COMMENT_SCORE_SIZE -= 0.5f;
            TextSizes.COMMENTS_BODY_SIZE--;
            TextSizes.COMMENTS_AUTHOR_SIZE -= 0.5f;
            TextSizes.COMMENTS_SCORE_SIZE -= 0.5f;

            activity.getAdapter().notifyDataSetChanged();
        }*/
    }

}
