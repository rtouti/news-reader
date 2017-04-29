package com.reddit.client.redditclient2.controllers.async;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.reddit.client.redditclient2.api.RedditClient;
import com.reddit.client.redditclient2.api.things.Account;
import com.reddit.client.redditclient2.controllers.activities.AccountActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;

/**
 * Created by raouf on 17-03-30.
 */

public class AccountFetchingTask extends AsyncTask<Object, Account, Account> {
    private MainActivity activity;

    public AccountFetchingTask(MainActivity activity){
        this.activity = activity;
    }

    @Override
    protected Account doInBackground(Object... params) {
        RedditClient client = new RedditClient();
        Account account = client.me().account();

        return account;
    }

    @Override
    protected void onPostExecute(Account account) {
        /*Intent intent = new Intent(activity.getApplicationContext(), AccountActivity.class);
        intent.putExtra("account", account);
        activity.startActivity(intent);*/
        activity.setAccount(account);
        ListView list = activity.getListView();
        TextView v = (TextView)list.getChildAt(list.getFirstVisiblePosition());
        v.setText(account.username);
        Log.i("DEBUG_ACCOUNT", account.username + "1");
    }
}
