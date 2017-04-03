package com.reddit.client.redditclient2.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;

/**
 * Created by raouf on 17-03-20.
 */

public class DrawerAdapter extends BaseAdapter {
    private MainActivity activity;
    private int[] drawerItems;

    public DrawerAdapter(MainActivity activity, int[] drawerItems){
        this.activity = activity;
        this.drawerItems = drawerItems;
    }

    @Override
    public int getCount() {
        return drawerItems.length;
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = activity.getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView text_view = (TextView)convertView.findViewById(android.R.id.text1);
        text_view.setTextSize(25.0f);
        if(position == 0 && RedditClient.CONNECTED){
            text_view.setText(R.string.my_account);
        }
        else {
            text_view.setText(drawerItems[position]);
        }

        return convertView;
    }

}
