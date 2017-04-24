package com.reddit.client.redditclient2.api.endpoints;

import android.util.Log;

import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by raouf on 17-02-25.
 */

public class SubredditLinksEndpoint implements Endpoint {
    public static final String SORITNG_HOT = "";
    public static final String SORITNG_TOP = "top";
    public static final String SORITNG_NEW = "new";
    public static final String SORITNG_CONTROVERSIAL = "controversial";
    public static final String TIME_SORTING_HOUR = "hour";
    public static final String TIME_SORTING_DAY = "day";
    public static final String TIME_SORTING_WEEK = "week";
    public static final String TIME_SORTING_MONTH = "month";
    public static final String TIME_SORTING_YEAR = "year";
    public static final String TIME_SORTING_ALL_TIME = "all";

    private static final int NUM_POST_PER_PAGE = 25;

    private String subreddit;

    private String url;
    private HttpRequestUtil http;
    private String body;
    private JSONObject json;
    private JSONObject data;

    private int count;

    public SubredditLinksEndpoint(String subreddit){
        this(subreddit, "", "", 0);
    }

    public SubredditLinksEndpoint(String subreddit, String after, String before, int count){
        this.subreddit = subreddit;
        this.count = count;

        this.url = Endpoint.REDDIT_BASE_URL + "/r/" + subreddit + "/" + PostFetchingTask.CURRENT_SORTING + ".json";
        this.http = new HttpRequestUtil(url);
        if(!after.equals("")){
            this.http.addQueryParameter("count", ""+count);
            this.http.addQueryParameter("after", after);
        }
        else if(!before.equals("")){
            this.http.addQueryParameter("count", ""+count);
            this.http.addQueryParameter("before", before);
        }
        if(!PostFetchingTask.CURRENT_TIME_SORTING.equals(SORITNG_HOT)){
            this.http.addQueryParameter("t", PostFetchingTask.CURRENT_TIME_SORTING);
        }

        this.http.makeRequest();
        this.body = http.getBodyString();
        Log.i("DEBUG", "body : "+this.body);
        Log.i("DEBUG", "url : "+this.url);
        try {
            this.json = new JSONObject(this.body);
            this.data = this.json.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Link> links(int limit) {
        ArrayList<Link> links = new ArrayList<>();

        try {
            JSONArray children = this.data.getJSONArray("children");
            JSONObject childData;

            int length = Math.min(limit, children.length());
            for(int i = 0; i < length; i++){
                childData = children.getJSONObject(i).getJSONObject("data");
                Link link = new Link();
                link.ups = childData.getInt("ups");
                link.downs = childData.getInt("downs");

                //"likes" peut être null
                try {
                    link.likes = childData.getBoolean("likes");
                }
                catch(JSONException e){
                    link.likes = null;
                }

                link.name = childData.getString("name");

                link.created = childData.getLong("created");
                link.created_utc = childData.getLong("created_utc");
                link.author = childData.getString("author");
                link.is_self = childData.getBoolean("is_self");
                link.numComments = childData.getInt("num_comments");
                link.permalink = childData.getString("permalink");
                link.score = childData.getInt("score");
                link.selftext = childData.getString("selftext");
                link.subreddit = childData.getString("subreddit");
                link.thumbnail = childData.getString("thumbnail");
                try {
                    link.highResPicture = childData.getJSONObject("preview")
                            .getJSONArray("images")
                            .getJSONObject(0)
                            .getJSONObject("source")
                            .getString("url");
                }
                catch(JSONException e){
                    link.highResPicture = "";
                }
                link.title = childData.getString("title");
                link.url = childData.getString("url");
                links.add(link);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return links;
    }

    public SubredditLinksEndpoint next(){
        String after = null;
        try {
            after = this.data.getString("after");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.count += NUM_POST_PER_PAGE;

        SubredditLinksEndpoint subreddit = new SubredditLinksEndpoint(this.subreddit, after, "", this.count);
        return subreddit;
    }

    public SubredditLinksEndpoint before(){
        String before = null;
        try {
            before = this.data.getString("before");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.count += 1;

        SubredditLinksEndpoint subreddit = new SubredditLinksEndpoint(this.subreddit, "", before, this.count);
        return subreddit;
    }
}
