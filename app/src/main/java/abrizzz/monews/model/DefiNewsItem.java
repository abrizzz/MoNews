package abrizzz.monews.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by brizzz on 4/27/16.
 */
public class DefiNewsItem extends NewsItem {
    private ArrayList<String> category;
    public DefiNewsItem(String title, URL link, GregorianCalendar datePublished, String creator, String description)
    {
        super(title,link,datePublished,creator,description,"Defi");
    }

    public ArrayList<String> getCategory()
    {
        return this.category;
    }

    public void setCategory(ArrayList<String> cat)
    {
        this.category = cat;
    }

    public void addCategory(String cat)
    {
        this.category.add(cat);
    }
}
