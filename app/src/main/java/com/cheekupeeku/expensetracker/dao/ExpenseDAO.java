package com.cheekupeeku.expensetracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.cheekupeeku.expensetracker.DatabaseHelper;
import com.cheekupeeku.expensetracker.model.Expense;

public class ExpenseDAO {
  public static boolean save(Context context, Expense e){
      boolean status = false;
      try {
          DatabaseHelper helper = new DatabaseHelper(context);
          SQLiteDatabase database = helper.getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put("category_id", e.getCategoryId());
          values.put("tag", e.getTag());
          values.put("amount", e.getAmount());
          values.put("payment_mode", e.getPaymentMode());
          values.put("edate", e.getEdate());
          database.insert("expense", null, values);
          status = true;
      }
      catch (Exception ee){
          Toast.makeText(context, ""+ee, Toast.LENGTH_SHORT).show();
      }
      return status;
  }
}
