package com.litto.expense;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tom on 2016/11/9.
 */

public class Expense implements Parcelable{
    int id;
    String cdate;
    String info;
    int amount;

    public Expense(Cursor cursor){
        // get all fields value from cursor
        id = cursor.getInt(
                cursor.getColumnIndex(ExpenseContracts.TableExpenses.COL_ID));
        cdate = cursor.getString(
                cursor.getColumnIndex(ExpenseContracts.TableExpenses.COL_CDATE));
        info = cursor.getString(
                cursor.getColumnIndex(ExpenseContracts.TableExpenses.COL_INFO));
        amount = cursor.getInt(
                cursor.getColumnIndex(ExpenseContracts.TableExpenses.COL_AMOUNT));
    }

    public Expense(int id, String cdate, String info, int amount) {
        this.id = id;
        this.cdate = cdate;
        this.info = info;
        this.amount = amount;
    }

    public Expense() {
    }

    protected Expense(Parcel in) {
        id = in.readInt();
        cdate = in.readString();
        info = in.readString();
        amount = in.readInt();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(cdate);
        parcel.writeString(info);
        parcel.writeInt(amount);
    }
}
