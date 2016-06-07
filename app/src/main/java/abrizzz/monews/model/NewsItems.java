package abrizzz.monews.model;



import android.support.v4.util.ArrayMap;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.DateCompare;


/**
 * Created by brizzz on 4/29/16.
 */
public class NewsItems {
    private static NewsItems singleton = null;
    private List<NewsItem> lexpressItems;
    private List<NewsItem> defiItems;
    private List<NewsItem> ionItems;
    private List<NewsItem> teleplusItems;

    private NewsItems()
    {
        lexpressItems = new ArrayList<NewsItem>();
        defiItems = new ArrayList<NewsItem>();
        ionItems = new ArrayList<NewsItem>();
        teleplusItems = new ArrayList<NewsItem>();
    }

    public static NewsItems getSingletonInstance()
    {
        if(singleton == null)
        {
            singleton = new NewsItems();
        }
        return singleton;
    }

    //Retungs a list of all NewsItem sorted by publish date
    public List<NewsItem> getAllItemsAsList()
    {
        List<NewsItem> l = new ArrayList<NewsItem>();
        l.addAll(defiItems);
        l.addAll(ionItems);
        l.addAll(teleplusItems);
        Collections.sort(l,new DateCompare());
        // Add L'Express at the end because it does not use proper pubDate tag
        l.addAll(lexpressItems);
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

    public void addTeleplusList(List<NewsItem> l){
        teleplusItems.addAll(l);
    }

    public void clearTeleplusItems()
    {
        teleplusItems.clear();
    }

    public List<NewsItem> getTeleplusItemsAsList(){ return this.teleplusItems; }
}
