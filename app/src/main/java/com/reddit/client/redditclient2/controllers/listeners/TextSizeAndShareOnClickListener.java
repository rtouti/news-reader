package com.reddit.client.redditclient2.controllers.listeners;

import android.view.View;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.utils.TextSizes;

/**
 * Created by raouf on 17-04-06.
 */

public class TextSizeAndShareOnClickListener implements View.OnClickListener {
    private ArticlesActivity activity;

    public TextSizeAndShareOnClickListener(ArticlesActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        //Augmenter la taille du texte
        if(v.getId() == R.id.text_size_up_button){
            TextSizes.FIRST_COMMENT_TITLES_SIZE++;
            TextSizes.FIRST_COMMENT_ARTICLE_SIZE++;
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
            TextSizes.FIRST_COMMENT_ARTICLE_SIZE--;
            TextSizes.FIRST_COMMENT_AUTHOR_SIZE -= 0.5f;
            TextSizes.FIRST_COMMENT_SCORE_SIZE -= 0.5f;
            TextSizes.COMMENTS_BODY_SIZE--;
            TextSizes.COMMENTS_AUTHOR_SIZE -= 0.5f;
            TextSizes.COMMENTS_SCORE_SIZE -= 0.5f;

            activity.getAdapter().notifyDataSetChanged();
        }
    }

}
