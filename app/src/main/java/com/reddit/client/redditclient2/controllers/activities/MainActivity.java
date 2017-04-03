package com.reddit.client.redditclient2.controllers.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.api.endpoints.SubredditLinksEndpoint;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.async.AuthenticationTask;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;
import com.reddit.client.redditclient2.controllers.listeners.ArticlesOnItemClickListener;
import com.reddit.client.redditclient2.controllers.listeners.DrawerOnItemClickListener;
import com.reddit.client.redditclient2.controllers.listeners.SortingTabsOnTabSelectedListener;
import com.reddit.client.redditclient2.views.adapters.ArticlesAdapter;
import com.reddit.client.redditclient2.views.adapters.DrawerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView articles_list_view;
    private ArticlesAdapter articles_adapter;
    private DrawerAdapter drawer_adapter;
    private ArrayList<Link> links;
    private boolean connected = false;
    private String currentSubreddit = "news";

    private String[] sortingItems = {
            "hot",
            "new",
            "top"
    };

    private int drawerItems[] = {
            R.string.drawer_connection,
            R.string.drawer_news,
            R.string.drawer_science,
            R.string.drawer_sports,
            R.string.drawer_economy,
            R.string.drawer_summaries,
    };

    private String drawerItemsStrings[] = {
            "Connection",
            "news",
            "science",
            "sports",
            "economy",
            "autotldr"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articles_list_view = (ListView)findViewById(R.id.articles_list_view);
        articles_adapter = new ArticlesAdapter(this);
        articles_list_view.setAdapter(articles_adapter);
        articles_list_view.setOnItemClickListener(new ArticlesOnItemClickListener(this));

        drawer_adapter = new DrawerAdapter(this, drawerItems);
        ListView drawer_list_view = (ListView)findViewById(R.id.drawer_list_view);
        drawer_list_view.setAdapter(drawer_adapter);
        drawer_list_view.setOnItemClickListener(new DrawerOnItemClickListener(this, drawerItemsStrings));

        TabLayout tab_layout = (TabLayout)findViewById(R.id.tab_layout);
        tab_layout.addOnTabSelectedListener(new SortingTabsOnTabSelectedListener(this));

        this.links = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        if(!RedditClient.CONNECTED && intent != null && intent.getAction() != null && intent.getAction().equals(Intent.ACTION_VIEW)){
            Uri uri = intent.getData();
            String error = uri.getQueryParameter("error");
            //Si pas d'erreur
            if(error == null){
                if(uri.getQueryParameter("state").equals(RedditClient.RANDOM_STRING)){
                    String code = uri.getQueryParameter("code");
                    AuthenticationTask task = new AuthenticationTask(this, code);
                    task.execute();
                }
            }
            //Si erreur
            else {
                Log.e("DEBUG", "Une erreur est survenue lors de l'authentification : "+error);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.preference_menu:
                setTheme(R.style.DarkAppTheme);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.sorting_menu:
                Toast.makeText(this, "Tri!", Toast.LENGTH_LONG).show();
                break;
            case R.id.text_size_menu:
                Toast.makeText(this, "Taille du texte!", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        return true;
    }

    

    public ArrayList<Link> getLinks(){
        return links;
    }

    public void addLinks(ArrayList<Link> newLinks){
        for(int i = 0; i < newLinks.size(); i++){
            this.links.add(newLinks.get(i));
        }
    }

    public DrawerAdapter getDrawerAdapter(){
        return drawer_adapter;
    }

    public boolean getConnected(){
        return connected;
    }

    public void setConnected(boolean connected){
        this.connected = connected;
    }

    public String getCurrentSubreddit(){
        return currentSubreddit;
    }

    public void changeCurrentSubreddit(String subreddit){
        links.clear();
        currentSubreddit = subreddit;
        articles_adapter = new ArticlesAdapter(this);
        articles_list_view.setAdapter(articles_adapter);

        changeSupportActionBarTitle(subreddit);
    }

    public void changeCurrentSubreddit(String subreddit, String sorting, String timeSorting){
        links.clear();
        currentSubreddit = subreddit;

        PostFetchingTask.CURRENT_SORTING = sorting;
        PostFetchingTask.CURRENT_TIME_SORTING = timeSorting;

        articles_adapter = new ArticlesAdapter(this);
        articles_list_view.setAdapter(articles_adapter);

        changeSupportActionBarTitle(subreddit);
    }

    private void changeSupportActionBarTitle(String subreddit){
        for(int i = 1; i < drawerItemsStrings.length; i++){
            if(subreddit.equals(drawerItemsStrings[i])){
                getSupportActionBar().setTitle(getResources().getString(drawerItems[i]));
            }
        }
    }


}
