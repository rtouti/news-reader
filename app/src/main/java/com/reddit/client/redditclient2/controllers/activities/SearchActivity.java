package com.reddit.client.redditclient2.controllers.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.async.SearchTask;
import com.reddit.client.redditclient2.controllers.listeners.SearchResultsOnItemClickListener;
import com.reddit.client.redditclient2.views.adapters.SearchResultsAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<Link> results;
    private ListView search_results_list_view;
    private SearchResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainActivity.THEME);
        setContentView(R.layout.activity_search);

        results = new ArrayList<>();

        Intent intent = getIntent();
        handleIntent(intent);

        search_results_list_view = (ListView)findViewById(R.id.search_results);
        adapter = new SearchResultsAdapter(this);
        search_results_list_view.setAdapter(adapter);
        search_results_list_view.setOnItemClickListener(new SearchResultsOnItemClickListener(this));
    }

    public void initAdapter(){
        int size = results.size();

        //S'il n'y a aucun resultat
        if(size == 0){
            adapter.setCount(1);
            adapter.setLoaded(true);
            adapter.setNoResults(true);
            adapter.notifyDataSetChanged();
        }
        //S'il y a au moins un resultat
        else {
            adapter.setCount(size);
            adapter.setLoaded(true);
            adapter.notifyDataSetChanged();
        }
    }

    public ArrayList<Link> getResults(){
        return results;
    }

    public void setResults(ArrayList<Link> results){
        this.results = results;
    }

    public SearchResultsAdapter getAdapter(){
        return adapter;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if(intent.getAction().equals(Intent.ACTION_SEARCH)){
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);

            SearchTask task = new SearchTask(this);
            task.execute(MainActivity.currentSubreddit, searchQuery);

            getSupportActionBar().setTitle(searchQuery);
        }
    }
}
