package com.reddit.client.redditclient2.api.things;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by raouf on 17-02-25.
 */

public class Comment extends Thing implements Votable, Created {
    //Variables from "votable" interface
    public int ups;
    public int downs;
    public Boolean likes;

    //Variables from "created" interface
    public long created;
    public long created_utc;

    public String author;
    public String body;
    public int gilded;
    //private boolean likes;
    public int score;
    public String subreddit;

    public JSONArray replies;



    public Comment(){

    }

    public ArrayList<Comment> replies(){
        if(replies == null)
            return new ArrayList<Comment>();

        ArrayList<Comment> comments = new ArrayList<>();

        try {
            JSONObject childData;
            for(int i = 0; i < replies.length()-1; i++){
                childData = replies.getJSONObject(i).getJSONObject("data");
                Comment comment = new Comment();
                comment.ups = childData.getInt("ups");
                comment.downs = childData.getInt("downs");

                //"likes" peut être null
                try {
                    comment.likes = childData.getBoolean("likes");
                }
                catch(JSONException e){
                    comment.likes = null;
                }

                comment.created = childData.getLong("created");
                comment.created_utc = childData.getLong("created_utc");
                comment.author = childData.getString("author");
                comment.body = childData.getString("body");
                comment.gilded = childData.getInt("gilded");
                comment.score = childData.getInt("score");
                comment.subreddit = childData.getString("subreddit");

                comment.replies = childData.getJSONObject("replies")
                        .getJSONObject("data")
                        .getJSONArray("children");

                comments.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comments;
    }

}
