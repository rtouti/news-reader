package com.reddit.client.redditclient2.api;

import com.reddit.client.redditclient2.api.endpoints.MeEndpoint;
import com.reddit.client.redditclient2.api.endpoints.PostCommentEndpoint;
import com.reddit.client.redditclient2.api.endpoints.SearchEnpoint;
import com.reddit.client.redditclient2.api.endpoints.SubredditLinksEndpoint;
import com.reddit.client.redditclient2.api.things.Thing;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;

/**
 * Created by raouf on 17-02-25.
 */

public class RedditClient {
    public static final String REDDIT_URL = "http://www.reddit.com/";
    public static final String REDDIT_OAUTH_URL = "https://oauth.reddit.com/";
    public static final String AUTHORIZATION_URL =
            "https://www.reddit.com/api/v1/authorize?client_id=%s&response_type=%s&" +
                    "state=%s&redirect_uri=%s&duration=%s&scope=%s";
    public static final String COMPACT_AUTHORIZATION_URL =
            "https://www.reddit.com/api/v1/authorize.compact?client_id=%s&response_type=%s&" +
                    "state=%s&redirect_uri=%s&duration=%s&scope=%s";
    public static final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
    public static final String POST_DATA = "grant_type=authorization_code&code=%s&redirect_uri=%s";

    public static final String CLIENT_ID = "FbuR6qU1Zra82Q";
    public static final String RANDOM_STRING = "HelloWorld";
    public static final String REDIRECT_URI = "http://localhost";

    public static String ACCESS_TOKEN = "";
    public static String REFRESH_TOKEN = "";

    public static boolean CONNECTED = false;


    public RedditClient(){

    }

    public static String getAuthorizationURL(boolean compact, String... scope){
        String scopes = "";
        for(int i = 0; i < scope.length-1; i++){
            scopes += scope[i] + ",";
        }
        scopes += scope[scope.length-1];

        return String.format(
                compact ? COMPACT_AUTHORIZATION_URL : AUTHORIZATION_URL,
                CLIENT_ID,
                "code",
                RANDOM_STRING,
                REDIRECT_URI,
                "permanent",
                scopes
        );
    }

    public static String getPostData(String code){
        return String.format(POST_DATA, code, REDIRECT_URI);
    }

    public void connect(String username, String password, String userAgent){

    }

    public SubredditLinksEndpoint subreddit(String subreddit){
        return new SubredditLinksEndpoint(subreddit);
    }

    public SubredditLinksEndpoint subreddit(String subreddit, String sorting, String timeSorting){
        PostFetchingTask.CURRENT_SORTING = sorting;
        PostFetchingTask.CURRENT_TIME_SORTING = timeSorting;

        return new SubredditLinksEndpoint(subreddit);
    }

    public void reply(String comment_text, Thing to){
        PostCommentEndpoint endpoint = new PostCommentEndpoint(comment_text, to.name);
    }

    public MeEndpoint me(){
        return new MeEndpoint();
    }

    public SearchEnpoint search(String subreddit, String searchQuery, boolean restrictOnSubreddit){
        return new SearchEnpoint(subreddit, searchQuery, restrictOnSubreddit);
    }

}