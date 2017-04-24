package com.reddit.client.redditclient2.HtmlParsing;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



/**
 * Contien le text de l'article et s'occupe du formatage. la classe principale est
 * composé d'une série de classe interne appelé Section contenant les textes
 */

public class ArticleText {
    private String title;
    private ArrayList<Section> sections;

    //----------------Classe interne-------------------
    public class Section{
        private String title;
        private String text;
        private int style;
        private ArrayList<String> listItems;

        public Section(String title, String text){
            this.title = title;
            this.text = text;
            this.style = 0;
        }

        public Section(String title, ArrayList<String> listItems){

            this.title = title;
            this.listItems = listItems;
            this.style = 1;
        }

        public int getStyle(){
            return style;

        }

        public String toString(){
            if(!title.equals(""))
                return title + "\n\n" + text;
            else return text;
        }
        /*
        *TODO:
        * je veut ajouter des propriété par exemple si un texte est en italique.
         * si le format est une liste etc.
         * pour utilisé dans formatForView
        */


        public CharSequence formatSectionParagraph(Context context){

            SpannableStringBuilder text;
            //spannableStringBuilder est une chaine de caractère avec la possibilité de modifier l'affichage
            //de certaine partie
            SpannableStringBuilder title;

            SpannableStringBuilder result = new SpannableStringBuilder();

            //Pour chaque section on met le titre 1.5fois plus grand et le text de grandeur normal

            title = new SpannableStringBuilder(this.title);
            text = new SpannableStringBuilder(this.text);
            title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            title.setSpan(new RelativeSizeSpan(1.5f), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (!(title.equals("") || text.equals("\n"))){
                result.append(title);
                result.append("\n\n");
            }

            if (!(text.equals("") || text.equals("\n"))){
                result.append(text);
                result.append("\n\n\n");
            }

            return result;
        }

        public CharSequence formatSectionList(Context context){

            SpannableStringBuilder text;

            SpannableStringBuilder title;

            SpannableStringBuilder result = new SpannableStringBuilder();

            title = new SpannableStringBuilder(this.title);
            text = new SpannableStringBuilder(this.listItems.get(0));
            title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            title.setSpan(new RelativeSizeSpan(1.5f), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.append(title);
            result.append("\n\n");
            for(int i=0; i<listItems.size(); i++){
                result.append("\u02da" + text);
                result.append("\n\n");
            }
            return result;
        }

    }

    //---------------debut class principal-----------------
    //le constructeur ne prend que le titre de Larticle en paramètre
    public ArticleText(String title){
        this.title = title;
        this.sections = new ArrayList<>();
    }

    //ajouter une nouvelle section
    public void addSection(String title, String text){
        sections.add(new Section(title, text));
    }

    public String toString(){
        String text = title + "\n\n";
        for(int i=0; i<sections.size(); i++){
            text += sections.get(i).toString() + "\n\n\n";
        }
        return text;
    }

    public void addSection(String title,  ArrayList<String> items){
        sections.add(new Section(title, items));
    }


    //Accessor
    public int size(){
        return sections.size();
    }

    public Section getSection(int i){
        return sections.get(i);
    }

    //fait le formatage (titre plus grand et en gras text normal, etc.)
    //voir la classe SpannableStringBuilder et l'interface Spannable
    public CharSequence formatForView(Context context){

        Section section;
        SpannableStringBuilder text;
        //spannableStringBuilder est une chaine de caractère avec la possibilité de modifier l'affichage
        //de certaine partie

        SpannableStringBuilder title = new SpannableStringBuilder();


        SpannableStringBuilder result = new SpannableStringBuilder();

        //met le titre en gras et plus gros de 1.8 fois la taille mit dans le textVIew
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new RelativeSizeSpan(1.8f), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //On ajoute à la chaine principale
        result.append(title);

        //Pour chaque section on met le titre 1.5fois plus grand et le text de grandeur normal
        for(int i=0; i<sections.size(); i++){
            section = sections.get(i);
            if(section.getStyle() == 0)
                result.append(section.formatSectionParagraph(context));

            else
                result.append(section.formatSectionList(context));
        }
        return result;
    }

}
