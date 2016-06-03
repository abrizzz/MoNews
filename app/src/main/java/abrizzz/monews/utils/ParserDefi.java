package abrizzz.monews.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import abrizzz.monews.R;
import abrizzz.monews.model.DefiNewsItem;
import abrizzz.monews.model.LexpressNewsItem;
import abrizzz.monews.model.NewsItem;
import abrizzz.monews.model.NewsItems;
import abrizzz.monews.viewcontroller.MainActivity;

/**
 * Created by brizzz on 4/27/16.
 */
public class ParserDefi extends AsyncTask<Void,Void,Void>{

    private Activity activity;
    private String item = "item";
    private String title = "title";
    private String link = "link";
    private String description = "description";
    private String creator = "creator";
    private String datePublished = "pubDate";
    private boolean done;
    private String format = "EEE, d MMM yyyy HH:mm:ss ZZZZZ";
    private String tmp = "";

    public ParserDefi(Activity a)
    {
        this.activity = a;
    }

    @Override
    protected Void doInBackground(Void... params) {
        NewsItem newItem = null;
        try{
            URL u = new URL(activity.getResources().getString(R.string.defi_source));
            URLConnection conn = u.openConnection();
            InputStream in = conn.getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(in, null);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_TAG)
                {
                    if(xpp.getName().equals(item))
                    {
                        newItem = new DefiNewsItem();
                        done = false;
                        while(!done) {
                            while (eventType != XmlPullParser.END_TAG) {
                                tmp = xpp.getText();
                                eventType = xpp.next();
                            }
                            if (xpp.getName().equals(title)) {
                                if (tmp!=null) {
                                    tmp = Html.fromHtml(tmp).toString();
                                    newItem.setTitle(tmp);
                                }
                            } else if (xpp.getName().equals(link)) {
                                newItem.setLink(new URL(tmp));
                            } else if (xpp.getName().equals(description)) {
                                if (tmp != null)
                                {
                                    tmp = Html.fromHtml(tmp).toString();
                                    newItem.setDescription(tmp);
                                }
                            } else if (xpp.getName().equals(creator)) {
                                newItem.setCreator(creator);
                            } else if (xpp.getName().equals(datePublished)) {
                                DateFormat df = new SimpleDateFormat(format);
                                Date d = df.parse(tmp);
                                GregorianCalendar cal = new GregorianCalendar();
                                cal.setTime(d);
                                newItem.setDatePublished(cal);
                            }else if (xpp.getName().equals(item))
                            {
                                done = true;
                            }
                            else
                            {
                                //Log.i("parser","Unrecognised tag: "+xpp.getName());
                            }
                            if (done == false)
                            {
                                eventType = xpp.next();
                            }
                        }
                        newItem.setSource(activity.getResources().getString(R.string.defi));
                        newItem.setRead(false);
                        NewsItems.getSingletonInstance().addDefiItem(newItem);
                    }
                }
                eventType = xpp.next();
            }
        }
        catch(XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        MainActivity ma = (MainActivity)activity;
        ma.updateList();
        super.onPostExecute(aVoid);
    }
}
