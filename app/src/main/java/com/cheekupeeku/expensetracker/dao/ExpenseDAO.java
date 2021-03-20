package com.cheekupeeku.expensetracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.cheekupeeku.expensetracker.DatabaseHelper;
import com.cheekupeeku.expensetracker.model.Expense;

import java.util.ArrayList;

public class ExpenseDAO {
  public static boolean deleteExpense(int id,Context context){
      boolean status = false;
      try{
          DatabaseHelper helper = new DatabaseHelper(context);
          SQLiteDatabase database = helper.getWritableDatabase();
          int i = database.delete("expense","id=?",new String[]{""+id});
          if(i!=0) {
              status = true;
              Toast.makeText(context, "Expense deleted", Toast.LENGTH_SHORT).show();
          }
      }
      catch (Exception e){
          Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
      }
      return status;
  }
  public static ArrayList<Expense> getExpenseList(Context context){
      ArrayList<Expense> al = new ArrayList<>();
      DatabaseHelper helper=  new DatabaseHelper(context);
      SQLiteDatabase database = helper.getReadableDatabase();
      String sql = "select expense.id,expense.category_id,expense.tag,expense.edate,expense.amount,expense.payment_mode,category.category_name from expense inner join category on expense.category_id = category.id order by expense.amount desc";
      Cursor c = database.rawQuery(sql,null);
      while(c.moveToNext()){
          int id = c.getInt(0);
          int cid = c.getInt(1);
          String tag = c.getString(2);
          String date = c.getString(3);
          int amount = c.getInt(4);
          String pm = c.getString(5);
          String cname = c.getString(6);

          Expense e = new Expense();
          e.setId(id);
          e.setCategoryId(cid);
          e.setTag(tag);
          e.setPaymentMode(pm);
          e.setEdate(date);
          e.setCategoryName(cname);
          e.setAmount(amount);
          al.add(e);
      }
      return al;
  }
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
