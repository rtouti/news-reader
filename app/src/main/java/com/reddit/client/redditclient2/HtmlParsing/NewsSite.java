package com.reddit.client.redditclient2.HtmlParsing;

/**
 * énumérateur contenenant les information sur certain site
 */

public enum NewsSite {
    BBC("bbc.com", "page", "", "story-body__inner", "story-body__h1"),
    CBS("cbsnews.com","article", "article-entry", "entry", "title"),
    NBC("nbcnews.com", "top", "", "article-body", "article-hed"),
    ESPN("espn.com","article-feed", "", "article-body", "article-header");


    String site, articleId, articleBodyId, articleBodyClass, titleId;

    //Pour retrouver un élément a partir d'un url
    public static NewsSite getSiteFromUrl(String url) {
        if (url.contains("bbc.com"))
            return BBC;
        if (url.contains("cbsnews.com"))
            return CBS;
        if (url.contains("nbcnews.com"))
            return NBC;
        if (url.contains("espn.com"))
            return ESPN;
        return null;
    }

    NewsSite(String site, String articleId, String articleBodyId, String articleBodyClass, String titleId) {
        this.site = site;
        this.articleId = articleId;
        this.articleBodyId = articleBodyId;
        this.articleBodyClass = articleBodyClass;
        this.titleId = titleId;
    }

    public String getSite() {
        return site;
    }

    public String getArticleId(){
        return articleId;
    }

    public String getIdName() {
        return articleBodyId;
    }

    public String getClassName() {
        return articleBodyClass;
    }

    public String getTitleName() {

        return titleId;
    }
}
