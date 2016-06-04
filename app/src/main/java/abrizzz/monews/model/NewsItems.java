package abrizzz.monews.model;



import android.support.v4.util.ArrayMap;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by brizzz on 4/29/16.
 */
public class NewsItems {
    private static NewsItems singleton = null;
    private List<NewsItem> lexpressItems;
    private List<NewsItem> defiItems;
    private List<NewsItem> ionItems;

    private NewsItems()
    {
        lexpressItems = new ArrayList<NewsItem>();
        defiItems = new ArrayList<NewsItem>();
        ionItems = new ArrayList<NewsItem>();
    }

    public static NewsItems getSingletonInstance()
    {
        if(singleton == null)
        {
            singleton = new NewsItems();
        }
        return singleton;
    }

    public List<NewsItem> getAllItemsAsList()
    {
        List<NewsItem> l = lexpressItems;
        l.addAll(defiItems);
        l.addAll(ionItems);
        // Randomise list because L'Express does not use proper pubDate tag
        Collections.shuffle(l);
        return l;
    }

    public void addLexpressList(List<NewsItem> l){
        lexpressItems.addAll(l);
    }

    public void clearLexpressItems()
    {
        lexpressItems.clear();
    }

    public void addDefiList(List<NewsItem> l){
        defiItems.addAll(l);
    }

    public void clearDefiItems()
    {
        defiItems.clear();
    }

    public void addIonList(List<NewsItem> l){
        ionItems.addAll(l);
    }

    public void clearIonItems()
    {
        ionItems.clear();
    }
}
