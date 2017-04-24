package com.reddit.client.redditclient2.controllers.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.reddit.client.redditclient2.api.endpoints.PostCommentEndpoint;
import com.reddit.client.redditclient2.api.endpoints.SubredditLinksEndpoint;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.async.AddCommentTask;
import com.reddit.client.redditclient2.controllers.async.ArticleTextFetchingTask;
import com.reddit.client.redditclient2.controllers.async.CommentsFetchingTask;
import com.reddit.client.redditclient2.controllers.listeners.AddCommentOnClickListener;
import com.reddit.client.redditclient2.controllers.listeners.CommentsOnItemClickListener;
import com.reddit.client.redditclient2.controllers.listeners.OnCommentSortingMultiChoiceClickListener;
import com.reddit.client.redditclient2.controllers.listeners.OnSortingMultiChoiceClickListener;
import com.reddit.client.redditclient2.controllers.listeners.TextSizeAndShareOnClickListener;
import com.reddit.client.redditclient2.views.adapters.ArticlesAdapter;
import com.reddit.client.redditclient2.views.adapters.CommentsAdapter;
import com.squareup.picasso.Picasso;

public class ArticlesActivity extends AppCompatActivity {
    private Link link;
    private CommentsAdapter adapter;
    private ListView comments;
    private CharSequence articleText = "Chargement de l'article";


    private String[] sortingItems = {
            CommentsFetchingTask.SORTING_APPRECIATED,
            CommentsFetchingTask.SORTING_NEW,
            CommentsFetchingTask.SORTING_CONTROVERSIAL,
            CommentsFetchingTask.SORTING_OLD
    };

    private String[] sortingItemsStrings = new String[4];

    private boolean[] sortingItemsSelection = new boolean[] {
            true, false, false, false
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.THEME);
        setContentView(R.layout.activity_article);


        sortingItemsStrings[0] = getResources().getString(R.string.comment_sorting_appreciated);
        sortingItemsStrings[1] = getResources().getString(R.string.comment_sorting_new);
        sortingItemsStrings[2] = getResources().getString(R.string.comment_sorting_controversial);
        sortingItemsStrings[3] = getResources().getString(R.string.comment_sorting_old);


        Intent intent = getIntent();
        this.link = (Link)intent.getSerializableExtra("link");

        adapter = new CommentsAdapter(this);
        comments = (ListView)findViewById(R.id.article_comments);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.articles_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_comment_menu:
                AddCommentTask task = new AddCommentTask(this);
                task.execute("Test", link);
            case R.id.comment_sorting_menu:
                Log.i("DEBUG", "comment sorting");
                AlertDialog alert = new AlertDialog.Builder(this)
                        .setSingleChoiceItems(
                                sortingItemsStrings,
                                0,
                                new OnCommentSortingMultiChoiceClickListener(this))
                        .create();
                Log.i("DEBUG", "comment sorting");
                alert.show();
                Log.i("DEBUG", "comment sorting");
            default:
                break;
        }

        return true;
    }

    public Link getLink(){
        return link;
    }

    public CharSequence getArticleText(){
        return articleText;
    }

    public void parseArticle(String url, String articleText){
        ArticleTextFetchingTask articleTask = new ArticleTextFetchingTask(articleText, this, url);
        articleTask.execute();

    }

    public void setArticleText(CharSequence articleText){
        this.articleText = articleText;
    }

    public CommentsAdapter getAdapter(){
        return adapter;
    }

    public String[] getSortingItems(){
        return sortingItems;
    }

    public void updateComment(String sorting){
        CommentsFetchingTask.CURRENT_SORTING = sorting;

        adapter = new CommentsAdapter(this);
        comments.setAdapter(adapter);
    }
}
