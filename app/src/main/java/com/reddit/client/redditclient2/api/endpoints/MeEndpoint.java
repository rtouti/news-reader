package com.reddit.client.redditclient2.api.endpoints;

import android.util.Base64;
import android.util.Log;

import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.api.things.Account;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raouf on 17-02-25.
 */

public class MeEndpoint implements Endpoint {
    public static final String ME_URL = RedditClient.REDDIT_OAUTH_URL + "api/v1/me.json";

    private HttpRequestUtil http;
    private String body;
    private JSONObject json;
    private JSONObject data;

    public MeEndpoint(){

    }

    public Account account(){
        this.http = new HttpRequestUtil(ME_URL);
        http.addHeader("User-Agent", "Android:232903:0.0.1 (by /u/enhancedelegance");
        this.http.addHeader(
                "Authorization",
                "bearer "+ RedditClient.ACCESS_TOKEN
        );
        this.http.makeRequest();
        this.body = http.getBodyString();
        Log.i("DEBUGaccount", "body : "+this.body);
        Log.i("DEBUGaccount", "url : "+ME_URL);

        Account account = new Account();
        try {
            this.json = new JSONObject(this.body);

            this.data = this.json;

            //account.comment_karma = data.getInt("comment_karma");
            //account.link_karma = data.getInt("link_karma");
            Log.i("DEBUGUSERNAME", data.getString("name"));
            account.username = data.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return account;
    }

}