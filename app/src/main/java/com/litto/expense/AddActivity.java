package com.litto.expense;

import android.content.ContentValues;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    private EditText edCdate;
    private EditText edInfo;
    private EditText edAmount;
    private ExpenseHelper helper;
    private boolean added = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edCdate = (EditText) findViewById(R.id.ed_cdate);
        edInfo = (EditText) findViewById(R.id.ed_info);
        edAmount = (EditText) findViewById(R.id.ed_amount);
        Button buttonAdd = (Button) findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        helper = ExpenseHelper.getInstance(this);
    }


    private void add() {
        String cdate = edCdate.getText().toString();
        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
        final String warningKey = "warning";
        boolean warning = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(warningKey, false);
        if (warning && amount > 100){
            Log.d(TAG, "warning");
            Snackbar.make(findViewById(R.id.activity_add), "High Amount expense!",
                    Snackbar.LENGTH_SHORT).show();
        }
        // TODO Error process
        if (cdate.length() > 0 && info.length() >0 && amount>0){
            ContentValues values = new ContentValues();
            values.put("cdate", cdate);
            values.put("info", info);
            values.put("amount", amount);
            // TODO make it Content Provider
//            long id = helper.getWritableDatabase().insert("expenses",
//                    null,
//                    values);
//            Log.d(TAG, "insert id:"+id);
            AddExpenseService.startActionInsert(this, values, true);
            setResult(RESULT_OK);
        }else{
            // TODO info user that data need to be enter
        }
    }
}
