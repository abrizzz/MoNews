package abrizzz.monews.model;

import java.net.URL;
import java.util.GregorianCalendar;

public class NewsItem {
    private String title;
    private URL link;
    private URL imageLink;
    private GregorianCalendar datePublished;
    private String creator;
    private String description;
    private String source;
    private Boolean read;

    public NewsItem() {
        super();
    }

   public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getLink() {
        return this.link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public GregorianCalendar getDatePublished() {
        return this.datePublished;
    }

    public void setDatePublished(GregorianCalendar d) {
        this.datePublished = d;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getRead() {
        return this.read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String toString()
    {
        return this.title;
    }

    public URL getImageLink() {
        return imageLink;
    }

    public void setImageLink(URL imageLink) {
        this.imageLink = imageLink;
    }
}