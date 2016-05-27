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

/**
 * Created by brizzz on 4/30/16.
 */
public class NewsArrayAdapter extends ArrayAdapter<NewsItem> {
    private Context context;
    private LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    private static class ViewContentHolder{
        public TextView titleTextView;
        public ImageView thumbnailImageView;
        public TextView subtitleTextView;
    }

    public NewsArrayAdapter(Context c, int textViewResourceId, List<NewsItem> objects)
    {
        super(c,textViewResourceId,objects);
        this.context = c;
    }

    public View getView(int position, View inflatedView, ViewGroup parent)
    {
        NewsItem n = getItem(position);
        if(inflatedView == null)
        {
            ViewContentHolder holder = new ViewContentHolder();
            inflatedView = inflater.inflate(R.layout.list_item,parent,false);
            holder.titleTextView = (TextView) inflatedView.findViewById(R.id.firstLine);
            holder.subtitleTextView = (TextView) inflatedView.findViewById(R.id.secondLine);
            holder.thumbnailImageView = (ImageView) inflatedView.findViewById(R.id.thumbnail);
        }

        ViewContentHolder holder = (ViewContentHolder) inflatedView.getTag();

        holder.titleTextView.setText(n.getTitle());
        holder.subtitleTextView.setText(n.getDescription());
        //set imageview to thunmbnail
        //set onclick listener
        return inflatedView;
    }
}
