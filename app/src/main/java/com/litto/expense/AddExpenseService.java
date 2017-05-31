package com.litto.expense;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AddExpenseService extends IntentService {
    private static final String TAG = AddExpenseService.class.getSimpleName();
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_INSERT = "com.litto.expense.action.INSERT";

    // TODO: Rename parameters
    private static final String EXTRA_VALUES = "com.litto.expense.extra.VALUES";
    public static final String ACTION_LAST_UPDATE = "com.litto.expense.UPDATE";
    private static final String EXTRA_LAST = "LAST";

//    private static boolean last;

    public AddExpenseService() {
        super("AddExpenseService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionInsert(Context context, ContentValues values, boolean last) {
        Log.d(TAG, "is last:"+last);
//        AddExpenseService.last = last;
        Intent intent = new Intent(context, AddExpenseService.class);
        intent.setAction(ACTION_INSERT);
        intent.putExtra(EXTRA_VALUES, values);
        intent.putExtra(EXTRA_LAST, last);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INSERT.equals(action)) {
                ContentValues values = intent.getParcelableExtra(EXTRA_VALUES);
                boolean last = intent.getBooleanExtra(EXTRA_LAST, false);
                Uri uri = getContentResolver().insert(ExpenseContracts.CONTENT_URI, values);
                if (uri == null){
                    Log.d(TAG, "insert failed");
                }else{
                    Log.d(TAG, "insert success");
                }
                if (last){
                    Intent update = new Intent();
                    update.setAction(ACTION_LAST_UPDATE);
                    sendBroadcast(update);
                    Log.d(TAG, "broadcast sent");
                }
            }
        }
    }

}
