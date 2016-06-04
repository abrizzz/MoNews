package abrizzz.monews.model;

import java.net.URL;
import java.util.GregorianCalendar;

import abrizzz.monews.R;

/**
 * Created by brizzz on 4/27/16.
 */
public class LexpressNewsItem extends NewsItem{
    public LexpressNewsItem(String title, URL link, GregorianCalendar datePublished, String creator, String description, String source, URL imageLink)
    {
        super(title,link,datePublished,creator,description, source, imageLink);
    }

    public LexpressNewsItem()
    {
        super();
    }
}
