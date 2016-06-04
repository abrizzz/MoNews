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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

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
    private List<NewsItem> list;
    private static class ViewContentHolder{
        public TextView titleTextView;
        public ImageView thumbnailImageView;
        public TextView subtitleTextView;
    }

   public NewsArrayAdapter(Context context) {
        super(context,R.layout.list_item);
        this.context = context;
        updateAllList();
    }

    public void updateAllList()
    {
        list = singletonInstance.getAllItemsAsList();
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
        titleView.setText(n.getTitle());
        if(n.getRead())
        {
            titleView.setTypeface(null, Typeface.NORMAL);
        }
        descriptionView.setText(n.getSource());
        thumbnailView.setImageDrawable(null);
        thumbnailView.setVisibility(View.GONE);
        final int color = getColor(n);
        sourceColor.setBackgroundResource(color);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n.setRead(true);
                Activity thisActivity = (Activity) context;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

                builder.setToolbarColor(context.getResources().getColor(color));
                builder.setShowTitle(true);

                //Set share action
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,n.getLink().toString());
                shareIntent.setType("*/*");
                Bitmap shareIcon = BitmapFactory.decodeResource(thisActivity.getResources(), R.drawable.ic_share);
                PendingIntent pendingShareIntent = PendingIntent.getActivity(context, 0, shareIntent, 0);
                builder.setActionButton(shareIcon,"Share",pendingShareIntent,true);

                // Does not work !?
                builder.setStartAnimations(thisActivity, R.anim.slide_in_right, R.anim.slide_out_left);
                builder.setExitAnimations(thisActivity, R.anim.slide_in_left, R.anim.slide_out_right);
                builder.setCloseButtonIcon(BitmapFactory.decodeResource(thisActivity.getResources(),R.drawable.ic_arrow_back));

                CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.launchUrl(thisActivity,Uri.parse(n.getLink().toString()));
            }
        });

        return rowView;
    }

    public int getColor(NewsItem n)
    {
        if(n.getSource().equals(context.getResources().getString(R.string.lexpress)))
        {
            return R.color.lexpressPrimary;
        }
        if(n.getSource().equals(context.getResources().getString(R.string.defi)))
        {
            return R.color.defiPrimary;
        }
        return R.color.colorPrimary;
    }
}
