package com.reddit.client.redditclient2.controllers.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.reddit.client.redditclient2.HtmlParsing.ArticleText;
import com.reddit.client.redditclient2.HtmlParsing.HtmlParser;
import com.reddit.client.redditclient2.HtmlParsing.NewsSite;
import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.async.ArticleTextFetchingTask;
import com.reddit.client.redditclient2.controllers.listeners.AddCommentOnClickListener;
import com.reddit.client.redditclient2.controllers.listeners.CommentsOnItemClickListener;
import com.reddit.client.redditclient2.controllers.listeners.TextSizeAndShareOnClickListener;
import com.reddit.client.redditclient2.views.adapters.CommentsAdapter;
import com.squareup.picasso.Picasso;

public class ArticlesActivity extends AppCompatActivity {
    private Link link;
    private CommentsAdapter adapter;
    private CharSequence articleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.THEME);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        this.link = (Link)intent.getSerializableExtra("link");

        adapter = new CommentsAdapter(this);
        ListView comments = (ListView)findViewById(R.id.article_comments);
        comments.setAdapter(adapter);
        comments.setOnItemClickListener(new CommentsOnItemClickListener(this));

        TextSizeAndShareOnClickListener listener = new TextSizeAndShareOnClickListener(this);
        ImageButton text_size_up_button = (ImageButton)findViewById(R.id.text_size_up_button);
        ImageButton text_size_down_button = (ImageButton)findViewById(R.id.text_size_down_button);
        ImageButton share_button = (ImageButton)findViewById(R.id.share_button);
        text_size_up_button.setOnClickListener(listener);
        text_size_down_button.setOnClickListener(listener);
        share_button.setOnClickListener(listener);

        //parseArticle(link.url, );
        //Log.i("article12", articleText.toString());
        //Toast.makeText(this, link.url, Toast.LENGTH_LONG).show();
    }

    public Link getLink(){
        return link;
    }

    public CharSequence getArticleText(){
        return articleText;
    }

    public void parseArticle(String url, TextView view){
        ArticleTextFetchingTask articleTask = new ArticleTextFetchingTask(view, this, url);
        articleTask.execute();

    }

    public CommentsAdapter getAdapter(){
        return adapter;
    }
}
