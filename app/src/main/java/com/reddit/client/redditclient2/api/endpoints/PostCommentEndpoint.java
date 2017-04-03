package com.reddit.client.redditclient2.api.endpoints;

import com.reddit.client.redditclient2.http.HttpRequestUtil;

/**
 * Created by raouf on 17-03-24.
 */

public class PostCommentEndpoint {
    public static final String POST_COMMENT_URL = "http://www.oauth.reddit.com/api/comment";

    private HttpRequestUtil http;

    public PostCommentEndpoint(String parentFullName){
        http = new HttpRequestUtil(POST_COMMENT_URL);
        http.addQueryParameter("", "");
    }

}
