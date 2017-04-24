package com.reddit.client.redditclient2.controllers.listeners;

import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.endpoints.SubredditLinksEndpoint;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;

import java.util.Locale;

/**
 * Created by raouf on 17-03-27.
 */

public class SortingTabsOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
    private MainActivity activity;

    public SortingTabsOnTabSelectedListener(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        String sorting = "";
        //String[] sortingItems = activity.getSortingItems();
        /*if(Locale.getDefault().getDisplayLanguage().equals("Français")){
            switch (tab.getText().toString()) {
                case "Populaires":
                    sorting = SubredditLinksEndpoint.SORITNG_HOT;
                    break;
                case "Récents":
                    sorting = SubredditLinksEndpoint.SORITNG_NEW;
                    break;
                case "Meilleurs":
                    sorting = SubredditLinksEndpoint.SORITNG_TOP;
                    break;
                default:
                    break;
            }
        }
        else if(Locale.getDefault().getDisplayLanguage().equals("English")) {
          */  switch (tab.getText().toString()) {
                case "Hot":
                    sorting = SubredditLinksEndpoint.SORITNG_HOT;
                    break;
                case "New":
                    sorting = SubredditLinksEndpoint.SORITNG_NEW;
                    break;
                case "Top":
                    sorting = SubredditLinksEndpoint.SORITNG_TOP;
                    Toast.makeText(activity, sorting, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }

        Toast.makeText(activity, sorting, Toast.LENGTH_LONG).show();

        activity.changeCurrentSubreddit(
                activity.getCurrentSubreddit(),
                sorting,
                SubredditLinksEndpoint.TIME_SORTING_ALL_TIME
        );
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
