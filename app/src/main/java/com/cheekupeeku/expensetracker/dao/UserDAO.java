package com.cheekupeeku.expensetracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cheekupeeku.expensetracker.DatabaseHelper;
import com.cheekupeeku.expensetracker.model.User;

public class UserDAO {
  public static boolean authenticateUser(Context context,User user){
      boolean status = false;
      // check in database table whether user exit or not
      DatabaseHelper helper= new DatabaseHelper(context);
      SQLiteDatabase database = helper.getReadableDatabase();
      Cursor c = database.rawQuery("select * from user where mobile=? and password = ?",new String[]{user.getMobile(),user.getPassword()});
      if(c.moveToNext())
          status = true;
      return status;
  }
  public static boolean save(Context context, User user){
     boolean status = false;
      DatabaseHelper helper = new DatabaseHelper(context);
      SQLiteDatabase database = helper.getWritableDatabase();
      ContentValues values = new ContentValues();
      values.put("mobile",user.getMobile());
      values.put("password",user.getPassword());
      long x = database.insert("user",null,values);
      if(x!=0)
          status = true;
      return status;
  }
}
