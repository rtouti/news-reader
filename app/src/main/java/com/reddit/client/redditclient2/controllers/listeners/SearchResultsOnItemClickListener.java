package com.reddit.client.redditclient2.controllers.listeners;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.activities.SearchActivity;

/**
 * Created by raouf on 17-04-07.
 */

public class SearchResultsOnItemClickListener implements AdapterView.OnItemClickListener {
    private SearchActivity activity;

    public SearchResultsOnItemClickListener(SearchActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Si ce n'est pas le list item "aucun résultat"
        if(activity.getAdapter().getLoaded() && !activity.getAdapter().getNoResults()){
            Link link = activity.getResults().get(position);

            Intent intent = new Intent(activity.getApplicationContext(), ArticlesActivity.class);
            intent.putExtra("link", link);
            activity.startActivity(intent);
        }
    }
}
