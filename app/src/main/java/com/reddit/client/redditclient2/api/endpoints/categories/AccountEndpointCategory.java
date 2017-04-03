package com.reddit.client.redditclient2.api.endpoints.categories;

import com.reddit.client.redditclient2.api.endpoints.Endpoint;
import com.reddit.client.redditclient2.api.endpoints.MeEndpoint;

/**
 * Created by raouf on 17-03-03.
 */

public class AccountEndpointCategory extends EndpointCategory {
    public Endpoint me;
    public Endpoint blocked;
    public Endpoint friends;
    public Endpoint messaging;
    public Endpoint trusted;
    public Endpoint where;

    public AccountEndpointCategory(){
        me = new MeEndpoint();
    }

}
