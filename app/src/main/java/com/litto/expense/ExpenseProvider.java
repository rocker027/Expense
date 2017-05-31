package com.litto.expense;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by tom on 2016/11/9.
 */

public class ExpenseProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);
    private static final int EXPENSES = 200;
    private static final int EXPENSES_WITH_ID = 201;
    private static final String TAG = "ExpenseProvider";

    static {
        sUriMatcher.addURI(ExpenseContracts.AUTHORITY,
                ExpenseContracts.TABLE_EXPENSES,
                EXPENSES);
        sUriMatcher.addURI(ExpenseContracts.AUTHORITY,
                ExpenseContracts.TABLE_EXPENSES + "/#",
                EXPENSES_WITH_ID);
    }

    ExpenseHelper helper;

    @Override
    public boolean onCreate() {
        helper = ExpenseHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case EXPENSES:
                break;
            case EXPENSES_WITH_ID:
                selection = ((selection == null) ? "" : selection);
                selection = new StringBuilder(selection)
                        .append(ExpenseContracts.TableExpenses.COL_ID)
                        .append("=")
                        .append(uri.getLastPathSegment())
                        .toString();
                Log.d(TAG, "selection: " + selection);
                break;
        }
        Cursor cursor =
                helper.getReadableDatabase().query(
                        ExpenseContracts.TABLE_EXPENSES,
                        projection, selection, selectionArgs, sortOrder, null, null);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert(ExpenseContracts.TABLE_EXPENSES,
                null,
                contentValues);
        if (id < 0)
            throw new SQLException("insert failed");
        Uri newUri = ContentUris.withAppendedId(uri, id);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        int id = helper.getWritableDatabase().delete(ExpenseContracts.TABLE_EXPENSES,
                where, whereArgs);
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
