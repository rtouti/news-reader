package com.reddit.client.redditclient2.utils;

import android.util.Log;

import com.reddit.client.redditclient2.api.things.Link;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by raouf on 17-03-09.
 */

public class ArrayUtils {

    //Suppose que tous les arraylists sont de la même taille
    public static final ArrayList<Link> special_merge_links(ArrayList<Link>... arrays){
        ArrayList<Link> links = new ArrayList<>();
        int[] listing = new int[arrays.length];
        for(int i = 0; i < listing.length; i++){
            listing[i] = i;
        }

        for(int i = 0; i < arrays[0].size(); i++){
            shuffle(listing);
            for(int j = 0; j < listing.length; j++){
                links.add(arrays[j].get(i));
            }
        }

        return links;
    }

    public static final int[] shuffle(int[] array){
        Random rand = new Random();
        for(int i = array.length-1; i > 0; i--){
            int k = Math.abs(rand.nextInt()) % i;
            int temp = array[i];
            array[i] = array[k];
            array[k] = temp;
        }

        return array;
    }

}
