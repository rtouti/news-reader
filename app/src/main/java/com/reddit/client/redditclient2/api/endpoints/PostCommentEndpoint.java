package com.reddit.client.redditclient2.api.endpoints;

import android.util.Log;

import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

/**
 * Created by raouf on 17-03-24.
 */

public class PostCommentEndpoint {
    public static final String POST_COMMENT_URL = RedditClient.REDDIT_OAUTH_URL + "api/comment.json";

    private HttpRequestUtil http;

    public PostCommentEndpoint(String comment_text, String parentFullName){
        http = new HttpRequestUtil(POST_COMMENT_URL);
        http.addHeader("User-Agent", "Android:232903:0.0.1 (by /u/enhancedelegance");
        this.http.addHeader(
                "Authorization",
                "bearer "+ RedditClient.ACCESS_TOKEN
        );
        //http.addQueryParameter("text", comment_text);
        //http.addQueryParameter("thing_id", parentFullName);
        http.addPostData("api_type=json&text="+comment_text+"&thing_id="+parentFullName);
        http.makeRequest();

        Log.i("DEBUG", "result : "+http.getBodyString());
        Log.i("DEBUG", "fullname : "+parentFullName);
        Log.i("DEBUG", "Access token : "+RedditClient.ACCESS_TOKEN);
        Log.i("DEBUG", "Post data : text="+comment_text+"&thing_id="+parentFullName);
    }

}