package abrizzz.monews.viewcontroller;

import android.content.Context;
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
        NewsItem n = singletonInstance.getLexpressItemsAsList().get(position);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView titleView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView descriptionView = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView thumbnailView = (ImageView) rowView.findViewById(R.id.thumbnail);
        titleView.setText(n.getTitle());
        descriptionView.setText(n.getDescription());
        thumbnailView.setImageResource(R.drawable.ic_menu_camera);
        return rowView;
    }
}
