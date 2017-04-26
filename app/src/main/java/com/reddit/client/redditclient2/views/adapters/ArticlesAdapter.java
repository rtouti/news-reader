package com.reddit.client.redditclient2.views.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.endpoints.SubredditLinksEndpoint;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;
import com.reddit.client.redditclient2.controllers.async.PostFetchingTask;
import com.reddit.client.redditclient2.http.HttpRequestUtil;
import com.reddit.client.redditclient2.utils.TextSizes;
import com.squareup.picasso.Picasso;

/**
 * Created by raouf on 17-03-18.
 */

public class ArticlesAdapter extends BaseAdapter {
    private MainActivity activity;
    private PostFetchingTask task;
    private SubredditLinksEndpoint subredditEndpoint;
    private int count = 0;
    private boolean loaded = false;

    public ArticlesAdapter(MainActivity activity){
        this.activity = activity;
    }

    public boolean getLoaded(){
        return loaded;
    }

    public void setLoaded(boolean loaded){
        this.loaded = loaded;
    }

    @Override
    public int getCount() {
        return count+1;
    }

    public void setCount(int count){
        this.count = count;
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
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.articles_list_item_layout, parent, false);
        }

        if(position == 0 && !this.loaded){
            this.task = new PostFetchingTask(this.activity, this.activity.getCurrentSubreddit(), this);
            this.task.execute();
            this.subredditEndpoint = this.task.getSubredditLinksEndpoint();
        }
        else if(position == this.activity.getLinks().size()-1){
            this.loaded = false;
            this.task = new PostFetchingTask(this.activity, this.activity.getCurrentSubreddit(), this);
            this.task.setSubredditLinksEndpoint(this.subredditEndpoint);

            this.task.execute("next");
        }

        if(position % 2 == 0){
            //convertView.setBackgroundColor(0x333);
            convertView.setBackgroundResource(R.color.list_item_grey);
        }
        else {
            //convertView.setBackgroundColor(0xEEE);
            convertView.setBackgroundResource(R.color.list_item_white);
        }

        TextView text = (TextView)convertView.findViewById(R.id.article_title);
        text.setTextSize(TextSizes.ARTICLES_TITLES_SIZE);

        ImageView image = (ImageView)convertView.findViewById(R.id.article_image);

        if(!this.loaded && position == this.activity.getLinks().size()){
            text.setText(R.string.loading);
            image.setVisibility(View.GONE);
            convertView.setMinimumHeight(50);

            //Si c'est un list item de loading, rendre invisible le bouton favori
            //convertView.findViewById(R.id.favorite).setVisibility(View.GONE);

        }
        else {
            Link link = activity.getLinks().get(position);

            String title = link.title;
            /*if(title.length() > 100){
                title = title.substring(0, 99) + "...";
            }*/
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

            //Si ce n'est pas un list item de loading, rendre visible le bouton favori
            //convertView.findViewById(R.id.favorite).setVisibility(View.VISIBLE);

            this.subredditEndpoint = this.task.getSubredditLinksEndpoint();
        }

        return convertView;
    }
}
