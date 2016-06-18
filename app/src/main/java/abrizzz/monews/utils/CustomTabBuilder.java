package abrizzz.monews.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.customtabs.CustomTabsIntent;

import java.net.URL;

import abrizzz.monews.R;

/**
 * Created by brizzz on 18/06/16.
 */
public class CustomTabBuilder {

    public static CustomTabsIntent Builder(Context context,int color, String url)
    {
        Activity thisActivity = (Activity) context;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        builder.setToolbarColor(context.getResources().getColor(color));
        builder.setShowTitle(true);

        //Set share action
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,url);
        shareIntent.setType("*/*");
        Bitmap shareIcon = BitmapFactory.decodeResource(thisActivity.getResources(), R.drawable.ic_share);
        PendingIntent pendingShareIntent = PendingIntent.getActivity(context, 0, shareIntent, 0);
        builder.setActionButton(shareIcon,"Share",pendingShareIntent,true);

        builder.setStartAnimations(thisActivity, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(thisActivity, R.anim.slide_in_left, R.anim.slide_out_right);
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(thisActivity.getResources(),R.drawable.ic_arrow_back));

        return builder.build();
    }
}
