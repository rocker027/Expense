package com.litto.expense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private Expense exp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        exp = getIntent().getParcelableExtra("EXPENSE");
        TextView tvCdate = (TextView) findViewById(R.id.detail_cdate);
        TextView tvInfo = (TextView) findViewById(R.id.detail_info);
        TextView tvAmount = (TextView) findViewById(R.id.detail_amount);
        tvCdate.setText(exp.getCdate());
        tvInfo.setText(exp.getInfo());
        tvAmount.setText(exp.getAmount()+"");
    }
}
