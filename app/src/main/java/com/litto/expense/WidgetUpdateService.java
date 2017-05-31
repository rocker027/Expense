package com.litto.expense;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by tom on 2016/11/25.
 */

public class WidgetUpdateService extends IntentService {

    private static final String TAG = WidgetUpdateService.class.getSimpleName();
    public WidgetUpdateService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, ExpenseAppWidget.class));
        for (int widgetId : widgetIds) {
            Cursor cursor = getContentResolver().query(ExpenseContracts.CONTENT_URI,
                    null, null, null, null);
            int total = 0;
            if (cursor != null) {
                total = cursor.getCount();
            }
            Random random = new Random();
            cursor.moveToPosition(random.nextInt(total));
            Expense exp = new Expense(cursor);
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.expense_app_widget);
            views.setTextViewText(R.id.appwidget_text, exp.getInfo());
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }
}
