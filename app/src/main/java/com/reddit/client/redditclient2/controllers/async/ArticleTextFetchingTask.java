package com.reddit.client.redditclient2.controllers.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.reddit.client.redditclient2.HtmlParsing.ArticleText;
import com.reddit.client.redditclient2.HtmlParsing.HtmlParser;
import com.reddit.client.redditclient2.HtmlParsing.NewsSite;
import com.reddit.client.redditclient2.controllers.activities.ArticlesActivity;
import com.reddit.client.redditclient2.controllers.activities.MainActivity;

/**
 * Created by olivier on 17-04-13.
 */

public class ArticleTextFetchingTask extends AsyncTask<String, TextView, CharSequence> {

    private ArticlesActivity context;
    private String articleText;
    private String url;

    public ArticleTextFetchingTask(String articleText, ArticlesActivity context, String url){
        this.articleText = articleText;
        this.context = context;
        this.url = url;
    }

    @Override
    protected CharSequence doInBackground(String... params) {
        //Utilisation de HtmlParser----------------

        //info sur les site particulier pas necessaire mais
        // pourrait être utile en cas de problème particulié

        NewsSite site = NewsSite.getSiteFromUrl(url);

        HtmlParser htmlParser = new HtmlParser(url); //url est le lien obtenue du json
        //parametre pur la fonction parsArticleBody
        String articleId;
        String articleBodyId;
        String articleClass;
        String articleTitle;

        //Il peuve être null
        if(site == null) {

            articleId = "";
            articleBodyId = "";
            articleClass = "";
            articleTitle = "";
        }
        else{
            articleId = site.getArticleId();

            articleBodyId = site.getIdName();
            articleClass = site.getClassName();
            articleTitle = site.getTitleName();
        }


        ArticleText article = htmlParser.parseArticleBody(articleId, articleBodyId, articleClass, articleTitle);
        CharSequence articleText;
        Log.i("DEBUG_ARTICLE", article == null ? "" : article.toString());
        if(article == null) {
            articleText = "";

        }
        else
            articleText = article.formatForView(context.getApplicationContext());


        return articleText;
    }
    @Override
    protected void onPostExecute(CharSequence articleText) {

        //articleView.setText(articleText);
        context.setArticleText(articleText);
        context.getAdapter().notifyDataSetChanged();

    }
}
