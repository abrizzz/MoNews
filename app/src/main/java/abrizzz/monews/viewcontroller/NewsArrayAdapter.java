package abrizzz.monews.viewcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import abrizzz.monews.R;
import abrizzz.monews.model.NewsItem;
import abrizzz.monews.model.NewsItems;

/**
 * Created by brizzz on 4/30/16.
 */
public class NewsArrayAdapter extends ArrayAdapter<NewsItem> {
    private Context context;
    private LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    private NewsItems singletonInstance = NewsItems.getSingletonInstance();

    private static class ViewContentHolder{
        public TextView titleTextView;
        public ImageView thumbnailImageView;
        public TextView subtitleTextView;
    }

   public NewsArrayAdapter(Context context) {
        super(context,R.layout.list_item);
        this.context = context;
    }

    @Override
    public int getCount() {
        return singletonInstance.getLexpressItemsAsList().size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NewsItem n = singletonInstance.getLexpressItemsAsList().get(position);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView titleView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView thumbnailView = (ImageView) rowView.findViewById(R.id.thumbnail);
        TextView sourceColor = (TextView) rowView.findViewById(R.id.sourceColor);
        titleView.setText(n.getTitle());
        descriptionView.setText(n.getSource());
        thumbnailView.setImageDrawable(null);
        thumbnailView.setVisibility(View.GONE);
        sourceColor.setBackgroundResource(R.color.lexpressBlue);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(n.getLink().toString()));
                //context.startActivity(browserIntent);
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                builder.setToolbarColor(context.getResources().getColor(R.color.lexpressBlue));
                Activity thisActivity = (Activity) context;
                customTabsIntent.launchUrl(thisActivity,Uri.parse(n.getLink().toString()));
            }
        });

        return rowView;
    }
}
