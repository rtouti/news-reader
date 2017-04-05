package com.reddit.client.redditclient2.api.endpoints;

import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by raouf on 17-04-05.
 */

public class SearchEnpoint implements Endpoint {
    private String subreddit;
    private String url;
    private HttpRequestUtil http;
    private String body;

    public SearchEnpoint(String subreddit, String searchQuery){
        this.subreddit = subreddit;

        url = REDDIT_BASE_URL + "/" + subreddit + "/search.json";
        http = new HttpRequestUtil(url);

        http.addQueryParameter("q", searchQuery);
        http.makeRequest();
        body = http.getBodyString();
    }

    public ArrayList<Link> links(int limit){
        ArrayList<Link> links = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(body);
            JSONArray children = json.getJSONObject("data").getJSONArray("children");

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
                link.title = childData.getString("title");
                link.url = childData.getString("url");
                links.add(link);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return links;
    }

}
