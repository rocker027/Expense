package com.litto.expense;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements ExpenseRecyclerAdapter.OnRecyclerViewItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivity";
    private static final int LOADER = 500;
    private static final int REQUEST_ADD = 200;
    private RecyclerView recyclerView;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AddExpenseService.ACTION_LAST_UPDATE)) {
                Log.d(TAG, "onReceive");
//                setupRecyclerView();
                getLoaderManager().restartLoader(LOADER, null, MainActivity.this);
            }
        }
    };
    private ExpenseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager cardLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(cardLayoutManager);
        // ListView
        ListView list = (ListView) findViewById(R.id.list);
        ExpenseHelper helper = ExpenseHelper.getInstance(this);
        Cursor c = helper.getReadableDatabase().query(
                "expenses", null, null, null, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.expense_row,
                c,
                new String[]{"_id", "cdate", "info", "amount"},
                new int[]{R.id.item_id, R.id.item_cdate,
                    R.id.item_info, R.id.item_amount},
                0);
        list.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddActivity.class)
                , REQUEST_ADD);
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        // insertRecord();
        // queryTest();
        // queryIdTest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD){
            if (resultCode == RESULT_OK){
                getLoaderManager().restartLoader(LOADER, null, this);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //
        IntentFilter filter = new IntentFilter(
                AddExpenseService.ACTION_LAST_UPDATE);
        registerReceiver(receiver, filter);

        setupRecyclerView();
        getLoaderManager().initLoader(LOADER, null, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver !=null){
            unregisterReceiver(receiver);
        }
    }

    private void setupRecyclerView() {
//        Cursor cursor = getContentResolver().query(
//                ExpenseContracts.CONTENT_URI, null, null, null, null);
//        Log.d(TAG, "row count:"+cursor.getCount());
        adapter = new ExpenseRecyclerAdapter();
        adapter.setOnRecyclerViewItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void queryIdTest() {
        Uri uri = ContentUris.withAppendedId(
                ExpenseContracts.CONTENT_URI, 1);
        Cursor cursor = getContentResolver().query(
                uri, null, null, null, null);
        Log.d(TAG, "row count: "+cursor.getCount());
//        getResources().getColor(R.color.colorAccent, getTheme());
//        ContextCompat.getColor(this, R.color.colorAccent);
    }

    private void queryTest() {
        Cursor cursor = getContentResolver().query(
                ExpenseContracts.CONTENT_URI, null, null, null, null);
        Log.d(TAG, "row count: "+cursor.getCount());
    }

    private void insertRecord() {
        ExpenseHelper helper = ExpenseHelper.getInstance(this);
        ContentValues values = new ContentValues();
        values.put("cdate", "2016-11-11");
        values.put("info", "Parking");
        values.put("amount", 40);
        helper.getWritableDatabase().insert(ExpenseContracts.TABLE_EXPENSES, null, values);
        helper.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, Expense exp) {
        Log.d(TAG, exp.getInfo());
        Intent detail = new Intent(this, DetailActivity.class);
        detail.putExtra("EXPENSE", exp);
        startActivity(detail);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG, "onCreateLoader");
        return new CursorLoader(this, ExpenseContracts.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished");
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset");
        adapter.swapCursor(null);
    }
}
