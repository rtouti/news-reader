package com.reddit.client.redditclient2.controllers.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Account;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent intent = getIntent();
        Account account = (Account)intent.getSerializableExtra("account");

        TextView username = (TextView)findViewById(R.id.username);
        TextView comment_karma = (TextView)findViewById(R.id.comment_karma);
        TextView link_karma = (TextView)findViewById(R.id.link_karma);
        username.setText(account.name);
        comment_karma.setText("Comment karma : "+account.comment_karma);
        link_karma.setText("Link karma : "+account.link_karma);
    }
}
