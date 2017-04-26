package com.reddit.client.redditclient2.controllers.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import com.reddit.client.redditclient2.controllers.listeners.OnSortingMultiChoiceClickListener;
import com.reddit.client.redditclient2.controllers.listeners.SortingTabsOnTabSelectedListener;
import com.reddit.client.redditclient2.views.adapters.ArticlesAdapter;
import com.reddit.client.redditclient2.views.adapters.DrawerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int DARK_THEME = R.style.DarkAppTheme;
    public static int NORMAL_THEME = R.style.AppTheme;
    public static int THEME = DARK_THEME;

    public static String currentSubreddit = "all";

    private ListView articles_list_view;
    private ArticlesAdapter articles_adapter;
    private DrawerAdapter drawer_adapter;
    private ArrayList<Link> links;
    private boolean connected = false;

    /*private String[] sortingItems = {
            getResources().getString(R.string.tab_hot),
            getResources().getString(R.string.tab_new),
            getResources().getString(R.string.tab_top)
    };*/

    private int drawerItems[] = {
            R.string.drawer_connection,
            R.string.drawer_all,
            R.string.drawer_news,
            R.string.drawer_worldnews,
            R.string.drawer_science,
            R.string.drawer_sports,
            R.string.drawer_economy,
            R.string.drawer_technology,
            R.string.drawer_cinema,
            R.string.drawer_books,
            R.string.drawer_summaries,
    };

    private String drawerItemsStrings[] = {
            "Connection",
            "all",
            "news",
            "worldnews",
            "science",
            "sports",
            "economy",
            "technology",
            "cinema",
            "books",
            "autotldr"
    };

    private String[] sortingItems = {
            SubredditLinksEndpoint.TIME_SORTING_HOUR,
            SubredditLinksEndpoint.TIME_SORTING_DAY,
            SubredditLinksEndpoint.TIME_SORTING_WEEK,
            SubredditLinksEndpoint.TIME_SORTING_MONTH,
            SubredditLinksEndpoint.TIME_SORTING_YEAR,
            SubredditLinksEndpoint.TIME_SORTING_ALL_TIME
    };

    private String[] sortingItemsStrings = new String[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(THEME);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //On initialise le tableau ici parce qu'il faut avoir crée l'activité
        //avant de pouvoir utiliser la méthode getResources()
        sortingItemsStrings[0] = getResources().getString(R.string.time_sorting_hour);
        sortingItemsStrings[1] = getResources().getString(R.string.time_sorting_day);
        sortingItemsStrings[2] = getResources().getString(R.string.time_sorting_week);
        sortingItemsStrings[3] = getResources().getString(R.string.time_sorting_month);
        sortingItemsStrings[4] = getResources().getString(R.string.time_sorting_year);
        sortingItemsStrings[5] = getResources().getString(R.string.time_sorting_all_time);


        //Quand on change de theme, le tab "hot" redevient selectionné par défaut alors
        //on remet le tri à "SORTING_HOT"
        PostFetchingTask.CURRENT_SORTING = SubredditLinksEndpoint.SORITNG_HOT;
        PostFetchingTask.CURRENT_TIME_SORTING = SubredditLinksEndpoint.TIME_SORTING_DAY;

        articles_list_view = (ListView)findViewById(R.id.articles_list_view);
        articles_adapter = new ArticlesAdapter(this);
        articles_list_view.setAdapter(articles_adapter);
        articles_list_view.setOnItemClickListener(new ArticlesOnItemClickListener(this));

        drawer_adapter = new DrawerAdapter(this, drawerItems);
        ListView drawer_list_view = (ListView)findViewById(R.id.drawer_list_view);

        if(THEME == NORMAL_THEME){
            drawer_list_view.setBackgroundColor(Color.WHITE);
        }
        else {
            drawer_list_view.setBackgroundColor(Color.DKGRAY);
        }

        DrawerLayout drawer_layout = (DrawerLayout)findViewById(R.id.activity_main);
        drawer_list_view.setAdapter(drawer_adapter);
        drawer_list_view.setOnItemClickListener(new DrawerOnItemClickListener(this, drawerItemsStrings, drawer_layout));

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

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    /*@Override
    public boolean onSearchRequested() {
        Toast.makeText(this, "Search!", Toast.LENGTH_LONG).show();

        return true;
    }*/



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.preference_menu:


                //Changer les themes
                THEME = (THEME == NORMAL_THEME) ? DARK_THEME : NORMAL_THEME;
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.sorting_menu:
                AlertDialog alert = new AlertDialog.Builder(this)
                        .setSingleChoiceItems(
                                sortingItemsStrings,
                                0,
                                new OnSortingMultiChoiceClickListener(this))
                        .create();
                alert.show();
                break;

            default:
                break;
        }

        return true;
    }



    public String[] getSortingItems(){
        return sortingItems;
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

    /*public String[] getSortingItems(){
        return sortingItems;
    }*/

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
