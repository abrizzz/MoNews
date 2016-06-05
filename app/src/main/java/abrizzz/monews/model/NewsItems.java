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
        List<NewsItem> l = new ArrayList<NewsItem>();
        // Mix list because L'Express does not use proper pubDate tag
        int i = 0;
        int totalSize = ionItems.size() + defiItems.size() + lexpressItems.size();
        while(l.size() < totalSize)
        {
            if(i < lexpressItems.size()){
                l.add(lexpressItems.get(i));
            }
            if(i < defiItems.size())
            {
                l.add(defiItems.get(i));
            }
            if(i < ionItems.size())
            {
                l.add(ionItems.get(i));
            }
            i++;
        }


        return l;
    }

    public void addLexpressList(List<NewsItem> l){
        lexpressItems.addAll(l);
    }

    public void clearLexpressItems()
    {
        lexpressItems.clear();
    }

    public List<NewsItem> getLexpressItemsAsList(){ return this.lexpressItems; }

    public void addDefiList(List<NewsItem> l){
        defiItems.addAll(l);
    }

    public void clearDefiItems()
    {
        defiItems.clear();
    }

    public List<NewsItem> getDefiItemsAsList(){ return this.defiItems; }

    public void addIonList(List<NewsItem> l){
        ionItems.addAll(l);
    }

    public void clearIonItems()
    {
        ionItems.clear();
    }

    public List<NewsItem> getIonItemsAsList(){ return this.ionItems; }
}
