package com.reddit.client.redditclient2.controllers.async;

import android.os.AsyncTask;
import android.util.Log;

import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.api.endpoints.SubredditLinksEndpoint;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.utils.ArrayUtils;
import com.reddit.client.redditclient2.views.adapters.ArticlesAdapter;

import java.util.ArrayList;

/**
 * Created by raouf on 17-03-18.
 */

public class PostFetchingTask extends AsyncTask<String, Object, ArrayList<Link>>{
    public static String CURRENT_SORTING = SubredditLinksEndpoint.SORITNG_HOT;
    public static String CURRENT_TIME_SORTING = SubredditLinksEndpoint.TIME_SORTING_DAY;

    private MainActivity activity;
    private String subreddit;
    private ArticlesAdapter adapter;
    private RedditClient client;
    private SubredditLinksEndpoint subredditEndpoint;

    public PostFetchingTask(MainActivity activity, String subreddit, ArticlesAdapter adapter){
        this.activity = activity;
        this.subreddit = subreddit;
        this.adapter = adapter;
        this.client = new RedditClient();
    }

    @Override
    protected ArrayList<Link> doInBackground(String... params) {
        if(this.subreddit.equals("all")){
            ArrayList<Link> science_links = client.subreddit("science").links(30);
            ArrayList<Link> news_links = client.subreddit("news").links(30);
            ArrayList<Link> sports_links = client.subreddit("sports").links(30);

            return ArrayUtils.special_merge_links(science_links, news_links, sports_links);
        }
        else {
            if(params.length == 0){
                this.subredditEndpoint = client.subreddit(this.subreddit);
                return this.subredditEndpoint.links(100);
            }
            else if(params[0].equals("next")){
                this.subredditEndpoint = this.subredditEndpoint.next();
                return this.subredditEndpoint.links(100);
            }
            else if(params[0].equals("before")){
                this.subredditEndpoint = this.subredditEndpoint.before();
                return this.subredditEndpoint.links(100);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Link> links) {
        this.activity.addLinks(links);

        this.adapter.setCount(this.activity.getLinks().size());
        this.adapter.notifyDataSetChanged();
        this.adapter.setLoaded(true);
    }

    public SubredditLinksEndpoint getSubredditLinksEndpoint(){
        return subredditEndpoint;
    }

    public void setSubredditLinksEndpoint(SubredditLinksEndpoint subredditEndpoint){
        this.subredditEndpoint = subredditEndpoint;
    }
}
