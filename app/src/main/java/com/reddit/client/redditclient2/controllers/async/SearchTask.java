package com.reddit.client.redditclient2.controllers.async;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.SearchActivity;
import com.reddit.client.redditclient2.views.adapters.SearchResultsAdapter;

import java.util.ArrayList;

/**
 * Created by raouf on 17-04-05.
 */

public class SearchTask extends AsyncTask<String, Object, ArrayList<Link>> {
    private SearchActivity activity;
    private RedditClient client;

    public SearchTask(SearchActivity activity){
        this.activity = activity;
    }

    @Override
    protected ArrayList<Link> doInBackground(String... params) {
        client = new RedditClient();

        ArrayList<Link> links = client.search(params[0], params[1]).links(100);

        return links;
    }

    @Override
    protected void onPostExecute(ArrayList<Link> links) {
        activity.setResults(links);

        activity.initAdapter();
    }
}
