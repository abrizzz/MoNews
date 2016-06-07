package abrizzz.monews.utils;

import java.util.Comparator;

import abrizzz.monews.model.NewsItem;

/**
 * Created by brizzz on 8/06/16.
 */
public class DateCompare implements Comparator<NewsItem> {
    @Override
    public int compare(NewsItem lhs, NewsItem rhs) {
        return (lhs.getDatePublished().compareTo(rhs.getDatePublished()) * -1);
    }
}
