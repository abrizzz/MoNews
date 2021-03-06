package abrizzz.monews.viewcontroller.viewmodel;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import abrizzz.monews.R;
import abrizzz.monews.model.NewsItem;
import abrizzz.monews.model.NewsItems;
import abrizzz.monews.utils.CustomTabBuilder;

/**
 * Created by brizzz on 4/30/16.
 */
public class NewsArrayAdapter extends ArrayAdapter<NewsItem> {
    private Context context;
    private LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    private NewsItems singletonInstance = NewsItems.getSingletonInstance();
    private List<NewsItem> list;
    public enum Sources {ALL, LEXPRESS, DEFI, ION, CINQPLUS, MAURICIEN, TELEPLUS}

    public Sources src = Sources.ALL;

    // TODO: Implement ViewContentHolder pattern
    private static class ViewContentHolder{
        public TextView titleView;
        public TextView descriptionView;
        public ImageView thumbnailView;
        public TextView sourceColor;
        public TextView dateView;
    }

   public NewsArrayAdapter(Context context) {
        super(context,R.layout.list_item);
        this.context = context;
        updateAllList();
    }

    public void updateAllList()
    {
        switch(src){
            case LEXPRESS:
                list = singletonInstance.getLexpressItemsAsList();
                break;
            case ION:
                list = singletonInstance.getIonItemsAsList();
                break;
            case DEFI:
                list = singletonInstance.getDefiItemsAsList();
                break;
            case CINQPLUS:
                list = singletonInstance.getCinqplusItemsAsList();
                break;
            case MAURICIEN:
                list = singletonInstance.getMauricienItemsAsList();
                break;
            case TELEPLUS:
                list = singletonInstance.getTeleplusItemsAsList();
                break;
            case ALL:
            default:
                list = singletonInstance.getAllItemsAsList();
                break;
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NewsItem n = list.get(position);

        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView titleView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView thumbnailView = (ImageView) rowView.findViewById(R.id.thumbnail);
        TextView sourceColor = (TextView) rowView.findViewById(R.id.sourceColor);
        TextView dateView = (TextView) rowView.findViewById(R.id.dateLine);

        titleView.setText(n.getTitle());
        //Set links that have been clicked on as not bold
        if(n.getRead())
        {
            titleView.setTypeface(null, Typeface.NORMAL);
            thumbnailView.setImageAlpha(155);
        }

        descriptionView.setText(n.getCreator() + " - " + n.getSource());
        dateView.setText(DateUtils.getRelativeTimeSpanString(n.getDatePublished().getTimeInMillis()));
        setImage(n,thumbnailView,context);

        final int color = getColor(n);
        sourceColor.setBackgroundResource(color);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n.setRead(true);
                CustomTabBuilder.Builder(context,getColor(n),n.getLink().toString()).launchUrl(((Activity)context),Uri.parse(n.getLink().toString()));
            }
        });

        return rowView;
    }

    private int getColor(NewsItem n)
    {
        if(n.getSource().equals(context.getResources().getString(R.string.lexpress)))
        {
            return R.color.lexpressPrimary;
        }
        if(n.getSource().equals(context.getResources().getString(R.string.defi)))
        {
            return R.color.defiPrimary;
        }
        if(n.getSource().equals(context.getResources().getString(R.string.ion)))
        {
            return R.color.ionPrimary;
        }
        if(n.getSource().equals(context.getResources().getString(R.string.cinqplus)))
        {
            return R.color.cinqplusPrimary;
        }
        if(n.getSource().equals(context.getResources().getString(R.string.teleplus)))
        {
            return R.color.teleplusPrimary;
        }
        if(n.getSource().equals(context.getResources().getString(R.string.mauricien)))
        {
            return R.color.mauricienDark;
        }
        return R.color.colorPrimary;
    }

    private void setImage(NewsItem n, ImageView thumbnailView, Context c)
    {
        if(n.getImageLink() != null)
        {
            Glide
                    .with(c).
                    load(Uri.parse(n.getImageLink().toString()))
                    .centerCrop()
                    .into(thumbnailView);
        }
        else{
            thumbnailView.setImageDrawable(null);
            thumbnailView.setVisibility(View.GONE);
        }
    }

}
