package com.reddit.client.redditclient2.controllers.async;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.http.HttpRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raouf on 17-03-13.
 */

public class AuthenticationTask extends AsyncTask<Object, Object, Object> {
    private MainActivity activity;
    private String code;

    public AuthenticationTask(MainActivity activity, String code){
        this.activity = activity;
        this.code = code;
    }

    @Override
    protected Object doInBackground(Object... params) {
        HttpRequestUtil requester = new HttpRequestUtil(RedditClient.ACCESS_TOKEN_URL);
        requester.addHeader("User-Agent", "Android:232903:0.0.1 (by /u/enhancedelegance");
        requester.addHeader(
                "Authorization",
                "Basic " + Base64.encodeToString((RedditClient.CLIENT_ID + ":" + "").getBytes(), Base64.NO_WRAP)
        );
        requester.addPostData(RedditClient.getPostData(code));
        requester.makeRequest();

        return requester;
    }

    @Override
    protected void onPostExecute(Object o) {
        HttpRequestUtil requester = (HttpRequestUtil)o;
        Log.i("DEBUG", requester.getBodyString());

        try {
            JSONObject responseJson = new JSONObject(requester.getBodyString());
            RedditClient.ACCESS_TOKEN = responseJson.getString("access_token");
            RedditClient.REFRESH_TOKEN = responseJson.getString("refresh_token");
            RedditClient.CONNECTED = true;
            Log.i("DEBUG", "Access token : "+RedditClient.ACCESS_TOKEN);
            Log.i("DEBUG", "Refresh token : "+RedditClient.REFRESH_TOKEN);

            activity.getDrawerAdapter().notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        activity.setConnected(true);
    }
}