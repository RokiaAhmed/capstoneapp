package com.ikdynmaics.capstoneapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;

import com.ikdynmaics.capstoneapp.data.cash.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                views.setImageViewBitmap(R.id.iv_icon, bitmap);
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) { }
        };

        Picasso.get().load(PreferenceUtils.read(context, "icon")).into(target);
        views.setTextViewText(R.id.tv_temp, PreferenceUtils.read(context, "temp") + "\u00B0");
        views.setTextViewText(R.id.tv_city, PreferenceUtils.read(context, "city"));
        views.setTextViewText(R.id.tv_description, PreferenceUtils.read(context, "desc"));
        views.setTextViewText(R.id.tv_min_temp, PreferenceUtils.read(context, "min_temp") + "\u00B0");
        views.setTextViewText(R.id.tv_max_temp, PreferenceUtils.read(context, "max_temp") + "\u00B0");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

