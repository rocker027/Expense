package com.litto.expense;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tom on 2016/11/9.
 */

public class ExpenseContracts {
    public static final String AUTHORITY = "com.litto.expense";
    public static final String TABLE_EXPENSES = "expenses";
    public static final class TableExpenses implements BaseColumns{
        public static final String COL_ID = "_id";
        public static final String COL_CDATE = "cdate";
        public static final String COL_INFO = "info";
        public static final String COL_AMOUNT = "amount";
    }
    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_EXPENSES).build();
}
