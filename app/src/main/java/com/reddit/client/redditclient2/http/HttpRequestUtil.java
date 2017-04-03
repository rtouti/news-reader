package com.reddit.client.redditclient2.http;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.HttpUrl;

import java.io.IOException;


/**
 * Created by raouf on 17-02-13.
 */

public class HttpRequestUtil {
    public static OkHttpClient client = new OkHttpClient();

    private HttpUrl.Builder urlBuilder;
    private Request.Builder requestBuilder;
    private String url;
    private String bodyString;

    public HttpRequestUtil(String url){
        this.urlBuilder = HttpUrl.parse(url)
                .newBuilder();
        this.requestBuilder = new Request.Builder();
    }

    public void addQueryParameter(String name, String value){
        this.urlBuilder.addQueryParameter(name, value);
    }

    public void addHeader(String name, String value){
        this.requestBuilder.addHeader(name, value);
    }

    public void addPostData(String data){
        this.requestBuilder.post(RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                data
        ));
    }

    public void makeRequest(){
        this.url = urlBuilder.toString();
        Request request = this.requestBuilder.url(this.url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            this.bodyString = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBodyString(){
        return this.bodyString;
    }

}
