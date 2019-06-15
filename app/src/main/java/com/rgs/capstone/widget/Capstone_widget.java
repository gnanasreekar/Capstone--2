package com.rgs.capstone.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.rgs.capstone.Details_choice;
import com.rgs.capstone.R;

/**
 * Implementation of App Widget functionality.
 */
public class Capstone_widget extends AppWidgetProvider {
    private static final String ACTION_BROADCASTWIDGETSAMPLE = "ACTION_BROADCASTWIDGETSAMPLE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Toast.makeText(context, "on updae", Toast.LENGTH_SHORT).show();
        }
    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        String data = null;
        SharedPreferences s = context.getSharedPreferences("myfile",Context.MODE_PRIVATE);
        if(s!=null)
        {
            data = s.getString("ALL","NO DATA AVAILABLE");
        }


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.capstone_widget);
        views.setTextViewText(R.id.appwidget_text, data);

        Intent intent = new Intent(context, Details_choice.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_capstone,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


}

