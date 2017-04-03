package com.reddit.client.redditclient2.api.things;

import java.io.Serializable;

/**
 * Created by raouf on 17-03-30.
 */

public class Account extends Thing implements Serializable{
    public String username;
    public int comment_karma;
    public int link_karma;

    public Account(){

    }

}
