package abrizzz.monews.model;



import android.support.v4.util.ArrayMap;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by brizzz on 4/29/16.
 */
public class NewsItems {
    private static NewsItems singleton = null;
    private List<NewsItem> lexpressItems;
    private List<NewsItem> defiItems;

    private NewsItems()
    {
        lexpressItems = new ArrayList<NewsItem>();
        defiItems = new ArrayList<NewsItem>();
    }

    public static NewsItems getSingletonInstance()
    {
        if(singleton == null)
        {
            singleton = new NewsItems();
        }
        return singleton;
    }

    public void addLexpressItem(NewsItem n) {
        lexpressItems.add(n);
    }

    public void addLexpressList(List<NewsItem> l){
        lexpressItems.addAll(l);
    }

    public void clearLexpressItems()
    {
        lexpressItems.clear();
    }

    public List<NewsItem> getLexpressItemsAsList()
    {
        return lexpressItems;
    }

    public void addDefiItem(NewsItem n) {
        defiItems.add(n);
    }

    public void addDefiList(List<NewsItem> l){
        defiItems.addAll(l);
    }

    public void clearDefiItems()
    {
        defiItems.clear();
    }

    public List<NewsItem> getDefiItemsAsList()
    {
        return defiItems;
    }
}
