package com.reddit.client.redditclient2.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.SearchActivity;
import com.reddit.client.redditclient2.utils.TextSizes;
import com.squareup.picasso.Picasso;

/**
 * Created by raouf on 17-04-06.
 */

public class SearchResultsAdapter extends BaseAdapter {
    private SearchActivity activity;
    private int count = 1;
    private boolean loaded = false;
    private boolean noResults = false;

    public SearchResultsAdapter(SearchActivity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setLoaded(boolean loaded){
        this.loaded = loaded;
    }

    public void setNoResults(boolean noResults){
        this.noResults = noResults;
    }

    public boolean getLoaded(){
        return loaded;
    }

    public boolean getNoResults(){
        return noResults;
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
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.articles_list_item_layout, parent, false);
        }

        TextView text = (TextView)convertView.findViewById(R.id.article_title);
        text.setTextSize(TextSizes.ARTICLES_TITLES_SIZE);
        ImageView image = (ImageView)convertView.findViewById(R.id.article_image);

        if(!loaded){
            text.setText(R.string.loading);
            image.setVisibility(View.GONE);

            return convertView;
        }
        else if(loaded && noResults){
            text.setText(R.string.no_results);
            image.setVisibility(View.GONE);

            return convertView;
        }

        Link link = activity.getResults().get(position);

        String title = link.title;
        if(title.length() > 100){
            title = title.substring(0, 99) + "...";
        }
        text.setText(title);

        String path = link.thumbnail;
        if(!path.equals("") && !path.equals("self") && !path.equals("image") && !path.equals("default")){
            Picasso.with(activity.getApplicationContext())
                    .load(path)
                    .into(image);
        }
        else {
            image.setVisibility(View.GONE);
        }

        return convertView;
    }

}
