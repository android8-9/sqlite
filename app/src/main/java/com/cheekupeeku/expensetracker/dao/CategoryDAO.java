package com.cheekupeeku.expensetracker.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cheekupeeku.expensetracker.DatabaseHelper;
import com.cheekupeeku.expensetracker.model.Category;

import java.util.ArrayList;

public class CategoryDAO {

    public static ArrayList<Category> getCategoryList(Context context){
        ArrayList<Category> al = new ArrayList<>(6);
        String sql = "select * from category";
        DatabaseHelper helper = new DatabaseHelper(context);

        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor c = database.rawQuery(sql,null);

        while(c.moveToNext()){
           int id =  c.getInt(0);
           String cName = c.getString(1);

           Category category = new Category(id,cName);

           al.add(category);
        }
       return al;
    }
}
