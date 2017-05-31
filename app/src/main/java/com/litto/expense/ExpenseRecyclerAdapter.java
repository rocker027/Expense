package com.litto.expense;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tom on 2016/11/9.
 */

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private Cursor cursor;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void swapCursor(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewItemClickListener(
            OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public ExpenseRecyclerAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Expense exp = new Expense(cursor);
        // set them to holder
        holder.cdateTextView.setText(exp.getCdate());
        holder.infoTextView.setText(exp.getInfo());
        holder.itemView.setTag(exp);
    }

    @Override
    public int getItemCount() {
        if (cursor == null){
            return 0;
        }else {
            return cursor.getCount();
        }
    }

    @Override
    public void onClick(View view) {
        if (onRecyclerViewItemClickListener != null){
            onRecyclerViewItemClickListener.onItemClick(view,
                    (Expense) view.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView cdateTextView;
        private final TextView infoTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            cdateTextView =
                    (TextView) itemView.findViewById(R.id.textViewCdate);
            infoTextView =
                    (TextView) itemView.findViewById(R.id.textViewInfo);
        }
    }
    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, Expense exp);
    }
}
