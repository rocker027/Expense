package com.litto.expense;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tom on 2016/11/9.
 */

public class ExpenseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "expense.db";
    private static final int DB_VERSION = 1;
    private static final String TAG = "ExpenseHelper";
    private static ExpenseHelper instance;
    private static Context context;
    private final Resources mResources;

    public static ExpenseHelper getInstance(Context ctx){
        if (instance==null){
            instance = new ExpenseHelper(ctx);
        }
        context = ctx;
        return instance;
    }

    private ExpenseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE expenses " +
                "(_id INTEGER PRIMARY KEY , " +
                "cdate DATETIME NOT NULL , " +
                "info VARCHAR, " +
                "amount INTEGER)");
        try {
            readExpensesFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void readExpensesFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.expenses);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        Log.d(TAG, sb.toString());
        JSONObject obj = new JSONObject(sb.toString());
        JSONArray expenses = obj.getJSONArray("expenses");
        for (int i = 0; i < expenses.length(); i++) {
            JSONObject item = expenses.getJSONObject(i);
            ContentValues values = new ContentValues(3);
            values.put(ExpenseContracts.TableExpenses.COL_CDATE,
                    item.getString("cdate"));
            values.put(ExpenseContracts.TableExpenses.COL_INFO,
                    item.getString("info"));
            values.put(ExpenseContracts.TableExpenses.COL_AMOUNT,
                    item.getString("amount"));
//            db.insert(ExpenseContracts.TABLE_EXPENSES, null, values);
            if (i < expenses.length()-1){
                AddExpenseService.startActionInsert(context, values, false);
            }else{
                AddExpenseService.startActionInsert(context, values, true);
            }
//            AddExpenseService.startActionInsert(context, values,
//                    (i==expenses.length()-1)? true: false);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
