package com.reddit.client.redditclient2.HtmlParsing;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
    Parse le html avec Jsoup pour extirper les éléments des articles
    voir DetailsActivity pour l'utilisatio+n
 **/

public class HtmlParser {

    String url;

    //On doit donner l'url de l'articel dans le constructeur;
    public HtmlParser(String url){

        this.url = url;

    }

    //va chercer le html et met l'arbre obtenue dans une classe Jsoup appeler Document
    public Document getHtml(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        return doc;
    }

    //Retourn un text brute de tous les élément
    //La classe n'est pas utilisé elle servait juste dans mes tests.
    public String getArticleText(String url) {

        String articleText = null;

        try {
            Element article = getHtml(url).getElementsByClass("story-body").get(0);
            articleText = article.text();
        } catch (IOException e) {
            Log.i("connection problem", "html parser");
        }
        return articleText;
    }



    //pour un élément donner la fonction retourn tous les enfants direct du noeud
    //qui on pour tag "tag"
    public Elements getElementsFromTag(Elements e, String tag){
        Elements result = new Elements();
        Element el;
        for(int i=0; i<e.size(); i++){
            el = e.get(i);
            if(el.tagName().equals(tag))
                result.add(el);
        }
        return result;
    }

    //Fait un parcour en profondeur a partir du noeud "span" et cherche le premier élément
    //qui a des enfant avec le tag "tag". Il retient le premier noeud dont le nombre de
    // caractère total contenue dans tous ces enfant(avec le tag "tag")  est
    // supérieur à 1000. Utilisé avec le tag "p" on peut déduire si une série d'élément "p"
    // représente le corp de l'article.
    public Element findArticleDiv(Element span, String tag){

        //regarde les enfants et fait la somme des caractère de enfant qui correspondent
        int somme = 0;
        //Elements pars = getElementsFromTag(span.children(), tag);
        Elements pars = span.children();

        for(int i = 0; i<pars.size(); i++){
            somme += pars.get(i).ownText().length();
        }

        //Si on a plus de 1000 on termine la récursion et on retourn span
        if(somme >= 500) {

            return span;
        }

        Element e = span;
        boolean found = false;
        Elements children = e.children();
        //Si span n'a pas d'enfant on termine sans solution
        if(children.size() == 0)
            return null;

        //on fait le parcour en profondeur
        Element child;
        for(int i=0; i<children.size(); i++){
            child = findArticleDiv(children.get(i), tag);

            //si on a trouver un élément on remonte l'arbre en le gardant en mémoir
            if(child != null) {
                Log.i("" + child.className(), "child");
                return child;

            }
        }
        //si on a parcouru tou les enfants sans rien trouver on retourne null;
        return null;
    }

    //Fonction utilise la fonction  findArticleDiv pour trouver les élément voulue.
    //et retourne un ArticleText.
    //je vais esseyer d'ajouter les image et les listes
    //On peut rajouter des paramètre pour être spécifique dans la recherche des élément.
    //j'ai ajouter des détails particulier pour certain site (dans NewsSite.java) mais l'algoritmhe de
    //recherche semble bien fonctionner sans avoir besoin de donné ces détail. je l,es ai laisser
    //au cas où:
    //-articleId: plus petit id contenant tout l'article
    //-bodyIdentifier: va chercher l'id du corp du texte ou si c'est vide on va chercher le classe dans bodyClass
    //-titleId: identifiant de la parti contenant le tire. pas variment nécessaire vu quon va le chercher
    //    avec l'api
    //tout fonctionne si on remplace es arguments par des "".
    public ArticleText parseArticleBody(String articleId, String bodyIdentifier, String bodyClass, String titleId){
        ArticleText article = null;
        ArrayList<String> list = new ArrayList<>();
        try {

            Element root = getHtml(url); //racine du html

            if(!articleId.equals(""))
                root = root.getElementById(articleId); //on peut être plus spécifique


            String mainTitle; //contient le titre de l'article

            if(titleId.equals(""))
                mainTitle = "";
            else
                mainTitle = root.getElementsByClass(titleId).text();//on peut aller le chercher avec l'id

            article = new ArticleText(mainTitle); //contient l'article

            Element body;

            //va chercher le corp de l'article avec la possibilité de spécifié un point de départ

            if(bodyIdentifier.equals("") && !bodyClass.equals(""))
                body = findArticleDiv(root.getElementsByClass(bodyClass).get(0), "p");//.parent();

            else if(!bodyIdentifier.equals(""))
                body = findArticleDiv(root.getElementById(bodyIdentifier), "p");//.parent();

            else
                body = findArticleDiv(root, "p");


            Elements parts = body.children(); //va chercher les partie du texte contenant l'article

            Element element;

            String text;
            String title = "";
            String tag;
            int index = 0;

            //on parcour toute les parties
            while (index < parts.size()) {
                text = "";
                element = parts.get(index);
                tag = element.tagName();
                //on parcour par section ie on concatene tous les paragaphes
                //jusqu'à ce quon arrive à un sous-titre
                while (index < parts.size() && !(tag.equals("h2") || tag.equals("h3"))){
                    if ((element.tagName()).equals("p") || (element.tagName()).equals("div")) {
                        if(element.text().length() > 1)
                            text += "\n\n" + element.text();

                    }

                    else if((element.tagName()).equals("ul") || (element.tagName()).equals("ol")){
                        Elements items = element.getElementsByTag("li");
                        for(int j=0; j<items.size(); j++)
                            list.add(items.get(j).text());

                    }

                    index++;
                    if(index < parts.size()) {
                        element = parts.get(index);
                        tag = element.tagName();
                    }
                }

                //au cas ou 'article commencerait par un sous-titre
                if (!text.equals("")) {

                    //on crée une nouvelle section avec le text obtenue et le titre précédent
                    article.addSection(title, text);

                }
                else if (!list.isEmpty()) {
                    article.addSection(title, list);
                    list.clear();
                }

                //On gare en mémoire le titre de la prochaine section
                title = parts.get(index).text();

                //au cas ou on ne passerait pas dans boucle interne
                if(text.equals("")) index++;

            }

        }catch(IOException e){}
        catch(Exception e){

        }
        finally {

            return article;
        }
    }

}
