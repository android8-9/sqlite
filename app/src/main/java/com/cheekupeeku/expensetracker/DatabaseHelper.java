package com.cheekupeeku.expensetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public DatabaseHelper(Context context){
        super(context,"expensedb.sqlite",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql  = "create table category(id integer primary key AUTOINCREMENT, category_name varchar(100))";
        db.execSQL(sql);

        sql = "insert into category(category_name) values('Tavelling'),('Fuel'),('Mobile recharge'),('Food'),('Other')";
        db.execSQL(sql);

        sql = "create table expense(id integer primary key AUTOINCREMENT, category_id integer, tag varchar(100),edate varchar(11),amount integer,payment_mode varchar(7))";
        db.execSQL(sql);

        sql = "create table user(mobile varchar(11),password varchar(10))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
