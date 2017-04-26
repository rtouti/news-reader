package com.reddit.client.redditclient2.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.reddit.client.redditclient2.R;
import com.reddit.client.redditclient2.api.things.Link;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.controllers.async.AddCommentTask;

/**
 * Created by olivier on 17-04-26.
 */

public class AddCommentDialog extends Dialog implements View.OnClickListener {
    private ArticlesActivity activity;
    private Link link;

    private TextView comment_view;

    public AddCommentDialog(@NonNull Context context, ArticlesActivity activity, Link link) {
        super(context);
        setContentView(R.layout.add_comment_view_layout);

        this.activity = activity;
        this.link = link;

        Button add_comment_button = (Button)findViewById(R.id.add_comment_button);
        add_comment_button.setOnClickListener(this);
        findViewById(R.id.comment_layout).setMinimumHeight(0);
        comment_view = (TextView)findViewById(R.id.add_comment_text);
    }

    @Override
    public void onClick(View v) {
        String text = comment_view.getText().toString();

        AddCommentTask task = new AddCommentTask(activity);
        task.execute(text, link);

        dismiss();
    }

}
